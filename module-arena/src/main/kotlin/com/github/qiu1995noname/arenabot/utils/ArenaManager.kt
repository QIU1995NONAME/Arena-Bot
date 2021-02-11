package com.github.qiu1995noname.arenabot.utils

import com.github.qiu1995noname.arenabot.entity.Cytus2Level
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsData
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.nameCardOrNick
import kotlin.math.floor

object ArenaManager {
    internal class ArenaContext(
        @Suppress("unused")
        val arenaId: Long,
    ) {
        val mutex = Mutex()
        val playing = HashMap<Long, Double?>()
        val prepare = HashSet<Long>()
        val cachedUser = HashMap<Long, Member>()

        @Suppress("unused")
        var operateTimeNs = System.nanoTime()
        var level: Cytus2Level? = null

        private fun getCachedNick(userId: Long): String {
            val user = cachedUser[userId]
            return "(${userId}) ${user?.nameCardOrNick}"
        }

        fun calculateResult(welcomeNew: Boolean = true): List<String> {
            val res = ArrayList<String>()
            val b = StringBuilder()
            if (playing.filter { it.value != null }.isNotEmpty()) {
                b.append("竞技场排行榜\n")
                var i = 0
                playing.filter { it.value != null }.toList().sortedByDescending { it.second }.forEach {
                    b.append("  ${++i}: ${it.second} \n    ${getCachedNick(it.first)}\n")
                }
            }
            res.add(b.toString())
            b.clear()

            // 所有准备中的，进入竞技场
            prepare.forEach { playing[it] = null }
            prepare.clear()
            // 清空成绩表
            b.append("有请下列挑战者进入竞技场：\n")
            playing.keys.forEach {
                playing[it] = null
                b.append("${getCachedNick(it)}\n")
            }
            if (welcomeNew && playing.isNotEmpty()) {
                res.add(b.toString())
            }
            // 清除选歌
            level = null
            return res
        }

        fun tryCalculateResult(): List<String> {
            if (!playing.values.none { it == null }) {
                return listOf("")
            }
            return calculateResult()
        }

        fun toStringWithNewLine(): String {
            val b = StringBuilder()
            b.append("游戏中的玩家:\n")
            playing.forEach {
                b.append("${getCachedNick(it.key)}\n")
                if (null != it.value) {
                    b.append("    ${it.value}\n")
                } else {
                    b.append("    [未提交成绩]\n")
                }
            }
            b.append("\n")
            //
            if (prepare.isNotEmpty()) {
                b.append("准备中的玩家:\n")
                prepare.forEach {
                    b.append("${getCachedNick(it)}\n")
                }
                b.append("\n")
            }
            //
            if (null == level) {
                b.append("[尚未抽歌]\n")
            } else {
                b.append("[当前歌曲]\n")
                b.append(level!!.toStringWithNewLine())
            }
            return b.toString()
        }
    }

    /**
     * 锁 要保证线程安全
     */
    private val mutex = Mutex()

    /**
     *  竞技场映射 MAP (arenaId => arenaContext)
     */
    private val ctxMap = HashMap<Long, ArenaContext>()

    /**
     * 线程安全的获取一个 Context
     *
     * @param arenaId   竞技场 ID
     * @param createNew 不存在时是否创建
     */
    private suspend fun getContext(
        arenaId: Long,
        createNew: Boolean = false,
        remove: Boolean = false,
    ): ArenaContext? {
        mutex.withLock {
            if (null != ctxMap[arenaId] && ctxMap[arenaId]!!.playing.isEmpty()) {
                ctxMap.remove(arenaId)
            }
            if (null == ctxMap[arenaId] && createNew) {
                ctxMap[arenaId] = ArenaContext(arenaId)
            }
            if (remove) {
                return ctxMap.remove(arenaId)
            }
            return ctxMap[arenaId]
        }
    }

    /**
     * 竞技状态
     */
    suspend fun status(arenaId: Long): String {
        val ctx = getContext(arenaId) ?: return "竞技场未开启"
        ctx.mutex.withLock {
            return ctx.toStringWithNewLine()
        }
    }

    /**
     * 尝试设置竞技曲目
     */
    suspend fun trySetLevel(arenaId: Long, user: Member, level: Cytus2Level): String {
        WhitelistsData.sampling(WhitelistsData.Feature.SONG_SELECT, user.group.id)
        val ctx = getContext(arenaId) ?: return "[非竞技]"
        ctx.mutex.withLock {
            // 未在竞技中
            if (user.id !in ctx.playing.keys) {
                return "[非竞技][未加入竞技]"
            }
            if (ctx.playing.values.filterNotNull().isNotEmpty()) {
                return "[非竞技][竞技运行中]"
            }
            ctx.level = level
            WhitelistsData.sampling(WhitelistsData.Feature.ARENA, user.group.id)
            return "[新的竞技曲目]"
        }
    }

    /**
     * 加入竞技
     */
    suspend fun createOrJoin(arenaId: Long, user: Member): String {
        // 无竞技 则直接创建
        val ctx = getContext(arenaId, createNew = true)
        WhitelistsData.sampling(WhitelistsData.Feature.ARENA, user.group.id)
        ctx!!.mutex.withLock {
            ctx.cachedUser[user.id] = user
            // 已经在竞技中
            if (user.id in ctx.playing.keys) {
                return "已在竞技场中"
            }
            // 已经在竞技准备组中
            if (user.id in ctx.prepare) {
                return "已在竞技场准备组中"
            }
            // 竞技运行中(已抽歌) 加入准备组
            if (null != ctx.level) {
                ctx.prepare.add(user.id)
                return "成功加入准备组"
            }
            // 直接加入
            ctx.playing[user.id] = null
            return "成功加入竞技场"
        }
    }

    /**
     * 提交成绩
     */
    suspend fun uploadScore(arenaId: Long, user: Member, score: Double): List<String> {
        val ctx = getContext(arenaId) ?: return listOf("竞技场未开启")
        ctx.mutex.withLock {
            if (user.id !in ctx.playing.keys) {
                return listOf("未进入竞技场")
            }
            if (null == ctx.level) {
                return listOf("未抽取歌曲")
            }

            if (score > 100) return listOf("成绩无效 ($score)")
            if (score < 80) return listOf("成绩过低 无法上传 ($score)")
            ctx.playing[user.id] = floor(score * 10000) / 10000

            WhitelistsData.sampling(WhitelistsData.Feature.ARENA, user.group.id)
            val res = ArrayList<String>()
            res.add("成绩提交成功 ($score)")
            res.addAll(ctx.tryCalculateResult())
            return res
        }
    }

    /**
     * 退出竞技
     */
    suspend fun exit(arenaId: Long, user: Member): List<String> {
        val ctx = getContext(arenaId) ?: return listOf("竞技场未开启")
        ctx.mutex.withLock {
            // 已经在竞技场列表 退出并刷新状态
            if (user.id in ctx.playing.keys) {
                ctx.playing.remove(user.id)
                val res = ArrayList<String>()
                res.add("退出成功")
                res.addAll(ctx.tryCalculateResult())
                return res
            }
            // 已经在竞技场准备组 直接退出
            if (user.id in ctx.prepare) {
                ctx.playing.remove(user.id)
                return listOf("退出成功[准备组]")
            }
            // 否则
            return listOf("未加入竞技场")
        }
    }

    /**
     * 强制结束一场竞技
     */
    suspend fun shutdown(arenaId: Long): List<String> {
        val ctx = getContext(arenaId, remove = true) ?: return listOf("竞技场未开启")
        ctx.mutex.withLock {
            val res = ArrayList<String>()
            res.addAll(ctx.calculateResult(welcomeNew = false))
            res.add("竞技场已结束")
            return res
        }
    }
}
