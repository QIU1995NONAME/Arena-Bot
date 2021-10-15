package com.github.qiu1995noname.arenabot.bot

import com.github.qiu1995noname.arenabot.entity.Cytus2Level
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsData
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.getMember
import net.mamoe.mirai.contact.nameCardOrNick
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

@Serializable
internal data class ArenaContext(
        val arenaId: Long,
        val playing: HashMap<Long, Double> = HashMap(),
        val prepare: HashSet<Long> = HashSet(),
        var operateTimeMs: Long = System.currentTimeMillis(),
        var level: Cytus2Level? = null
) {
    @Contextual
    @Transient
    val mutex = ReentrantReadWriteLock()
    private fun getCachedNick(bot: Bot, userId: Long): String {
        // TODO Upgrade it
        return "(${userId}) ${bot.getGroup(arenaId)?.getMember(userId)?.nameCardOrNick}"
    }

    //
    fun calculateResult(bot: Bot, welcomeNew: Boolean = true): List<String> = mutex.write {
        val res = ArrayList<String>()
        val b = StringBuilder()
        if (playing.filter { !it.value.isNaN() }.isNotEmpty()) {
            b.append("竞技场排行榜\n")
            var i = 0
            playing.filter { !it.value.isNaN() }.toList().sortedByDescending { it.second }.forEach {
                b.append(String.format("  ${++i}: %.2f \n    ${getCachedNick(bot, it.first)}\n", it.second))
            }
        }
        res.add(b.toString())
        b.clear()

        // 所有准备中的，进入竞技场
        prepare.forEach { playing[it] = Double.NaN }
        prepare.clear()
        // 清空成绩表
        b.append("有请下列挑战者进入竞技场：\n")
        playing.keys.forEach {
            playing[it] = Double.NaN
            b.append("${getCachedNick(bot, it)}\n")
        }
        if (welcomeNew && playing.isNotEmpty()) {
            res.add(b.toString())
        }
        // 清除选歌
        level = null
        // 重置超时
        operateTimeMs = System.currentTimeMillis()
        return res
    }

    fun tryCalculateResult(bot: Bot): List<String> = mutex.read {
        // 如果并不是所有人都提交了成绩
        if (!playing.values.none { it.isNaN() }) {
            // 不做处理
            return listOf("")
        }
        // 进行结算
        return calculateResult(bot)
    }

    fun toStringWithNewLine(bot: Bot): String {
        val b = StringBuilder()
        b.append("游戏中的玩家:\n")
        playing.forEach {
            b.append("${getCachedNick(bot, it.key)}\n")
            if (!it.value.isNaN()) {
                b.append(String.format("    %.2f\n", it.value))
            } else {
                b.append("    [未提交成绩]\n")
            }
        }
        b.append("\n")
        //
        if (prepare.isNotEmpty()) {
            b.append("准备中的玩家:\n")
            prepare.forEach {
                b.append("${getCachedNick(bot, it)}\n")
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

@Serializable
internal data class ArenaContexts(
        val contextMap: HashMap<Long, ArenaContext> = HashMap()
)

internal object ArenaBotData : AutoSavePluginData("ArenaBotData") {
    /**
     * 主锁 要保证线程安全
     */
    @Contextual
    @Transient
    private val mutex = ReentrantReadWriteLock()

    /**
     *  竞技场映射 MAP (arenaId => arenaContext)
     */
    private val contexts by value<ArenaContexts>()

    ///////////////////////////////////////////////////////////////////
    /**
     * 清理 不需要的 竞技上下文
     * 定时执行，每个竞技指令都要执行一次
     */
    private fun clearContext() = mutex.write {
        val removeSet = HashSet<Long>()
        val now = System.currentTimeMillis()
        for (item in contexts.contextMap) {
            item.value.mutex.read {
                // 如果长时间没有人操作，移除
                if (now - item.value.operateTimeMs > 15L * 60 * 1000) {
                    ArenaBotPlugin.logger.verbose("Arena ${item.key} shutting down by timeout.")
                    removeSet.add(item.key)
                }
                // 如果竞技场里面没有人，移除
                if (item.value.playing.isEmpty()) {
                    ArenaBotPlugin.logger.verbose("Arena ${item.key} shutting down by empty.")
                    removeSet.add(item.key)
                }
            }
        }
        for (key in removeSet) {
            contexts.contextMap.remove(key)
        }
    }

    /**
     * 异步安全的获取一个 Context
     *
     * @param arenaId   竞技场 ID
     * @param createNew 不存在时是否创建
     * @param remove    若存在是否移除
     */
    private fun getContext(
            arenaId: Long,
            createNew: Boolean = false,
            remove: Boolean = false,
    ): ArenaContext? = mutex.write {
        // 先清理
        clearContext()
        // 是否需要新建
        if (null == contexts.contextMap[arenaId] && createNew) {
            contexts.contextMap[arenaId] = ArenaContext(arenaId)
        }
        // 是否需要移除
        if (remove) {
            return contexts.contextMap.remove(arenaId)
        }
        return contexts.contextMap[arenaId]
    }

    /**
     * 强制结束一场竞技
     */
    fun shutdown(bot: Bot, arenaId: Long): List<String> {
        // 获取
        val ctx = getContext(arenaId, remove = true) ?: return listOf("竞技场未开启")
        ctx.mutex.read {
            val res = ArrayList<String>()
            res.addAll(ctx.calculateResult(bot, welcomeNew = false))
            res.add("竞技场已结束")
            return res
        }
    }

    /**
     * 退出竞技
     */
    fun exit(bot: Bot, arenaId: Long, userId: Long): List<String> {
        // 获取
        val ctx = getContext(arenaId) ?: return listOf("竞技场未开启")
        // 退出
        ctx.mutex.write {
            // 重置超时
            ctx.operateTimeMs = System.currentTimeMillis()
            // 已经在竞技场列表 退出并刷新状态
            if (userId in ctx.playing.keys) {
                ctx.playing.remove(userId)
                val res = ArrayList<String>()
                res.add("退出成功")
                res.addAll(ctx.tryCalculateResult(bot))
                return res
            }
            // 已经在竞技场准备组 直接退出
            if (userId in ctx.prepare) {
                ctx.playing.remove(userId)
                return listOf("退出成功[准备组]")
            }
            // 否则
            return listOf("未加入竞技场")
        }
    }

    /**
     * 提交成绩
     */
    fun uploadScore(bot: Bot, arenaId: Long, userId: Long, score: Double): List<String> {
        val ctx = getContext(arenaId) ?: return listOf("竞技场未开启")
        ctx.mutex.write {
            if (userId !in ctx.playing.keys) {
                return listOf("未进入竞技场")
            }
            if (null == ctx.level) {
                return listOf("未抽取歌曲")
            }

            if (score > 100) return listOf(String.format("成绩无效 (%.2f)", score))
            if (score < 80) return listOf(String.format("成绩过低 无法上传 (%.2f)", score))

            // 重置超时
            ctx.operateTimeMs = System.currentTimeMillis()
            ctx.playing[userId] = score

            WhitelistsData.sampling(WhitelistsData.Feature.ARENA, arenaId)
            val res = ArrayList<String>()
            res.add(String.format("成绩提交成功 (%.2f)", score))
            res.addAll(ctx.tryCalculateResult(bot))
            return res
        }
    }

    /**
     * 加入竞技
     *
     * TODO 重构此处的逻辑，不直接返回提示词，而是返回状态码
     */
    fun createOrJoin(bot: Bot, arenaId: Long, userId: Long): String {
        bot.isOnline // UNUSED

        // 无竞技 则直接创建
        val ctx = getContext(arenaId, createNew = true)
        WhitelistsData.sampling(WhitelistsData.Feature.ARENA, arenaId)
        ctx!!.mutex.write {
            // 已经在竞技中
            if (userId in ctx.playing.keys) {
                return "已在竞技场中"
            }
            // 已经在竞技准备组中
            if (userId in ctx.prepare) {
                return "已在竞技场准备组中(自动加入下一轮竞技)"
            }
            // 竞技运行中(已抽歌) 加入准备组
            if (null != ctx.level) {
                ctx.prepare.add(userId)
                return "成功加入准备组(自动加入下一轮竞技)"
            }
            // 直接加入
            ctx.playing[userId] = Double.NaN
            // 重置超时
            ctx.operateTimeMs = System.currentTimeMillis()
            return "成功加入竞技场"
        }
    }

    /**
     * 尝试设置竞技曲目
     */
    fun trySetLevel(bot: Bot, arenaId: Long, userId: Long, level: Cytus2Level): String {
        ArenaBotPlugin.logger.info("Bot: ${bot.id}")
        WhitelistsData.sampling(WhitelistsData.Feature.SONG_SELECT, arenaId)
        val ctx = getContext(arenaId) ?: return "[非竞技]"
        ctx.mutex.write {
            // 未在竞技中
            if (userId !in ctx.playing.keys) {
                return "[非竞技][未加入竞技]"
            }
            // 已经有人提交了成绩
            if (ctx.playing.values.any { !it.isNaN() }) {
                return "[非竞技][竞技运行中]"
            }
            ctx.level = level
            // 重置超时
            ctx.operateTimeMs = System.currentTimeMillis()
            WhitelistsData.sampling(WhitelistsData.Feature.ARENA, arenaId)
            return "[新的竞技曲目]"
        }
    }

    /**
     * 竞技状态
     */
    fun status(bot: Bot, arenaId: Long): String {
        val ctx = getContext(arenaId) ?: return "竞技场未开启"
        ctx.mutex.read {
            return ctx.toStringWithNewLine(bot)
        }
    }
}
