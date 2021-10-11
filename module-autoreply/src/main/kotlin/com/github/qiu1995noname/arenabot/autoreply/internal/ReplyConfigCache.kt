package com.github.qiu1995noname.arenabot.autoreply.internal

import com.github.qiu1995noname.arenabot.autoreply.AutoReplyCache
import com.github.qiu1995noname.arenabot.autoreply.AutoReplyPlugin
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.contact.getMember
import java.io.File
import java.security.MessageDigest
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

internal class ReplyConfigCache(
        val tempMode: Boolean = false
) {
    private val rwLock = ReentrantReadWriteLock()
    private val configList = ArrayList<ReplyConfig>()
    private val indexMessageConfig = HashMap<String, Int>()

    // SHA-512 -> Content
    private val fileCache = HashMap<ByteArray, ByteArray>()

    private fun cacheFile(fileName: String): ByteArray? {
        if (fileName.isEmpty()) return null
        AutoReplyPlugin.logger.verbose("Caching file: \"$fileName\" ...")
        val fileBuffer = File(fileName).readBytes()
        val checksum = MessageDigest.getInstance("SHA-512").digest(fileBuffer)
        synchronized(fileCache) {
            if (!fileCache.containsKey(checksum)) {
                fileCache[checksum] = fileBuffer
            }
            return fileCache[checksum]
        }
    }

    fun clear() = rwLock.write {
        configList.clear()
    }

    fun moveFrom(cache: ReplyConfigCache) = rwLock.write {
        if (tempMode || !cache.tempMode) {
            throw IllegalStateException("只可以从 temp 模式的 cache 移动到非 temp 模式的 cache")
        }
        configList.clear()
        configList.addAll(cache.configList)
        cache.configList.clear()
        // 同时重建索引
        configList.forEachIndexed { index, config ->
            // 将所有的 messages 进行索引
            config.messages.forEach {
                indexMessageConfig[it] = index
            }
            config.specialReplies.clear()
            // 将所有 config 里面的 special 进行索引
            config.replies.forEach {
                var key = AutoReplyCache.kDefaultSpecial
                if (it.specials.isNotEmpty()) {
                    key = it.specials.first()
                    // TODO fit for more specials
                }
                if (!config.specialReplies.containsKey(key)) {
                    config.specialReplies[key] = ArrayList()
                }
                config.specialReplies[key]!!.add(it)
            }
        }
    }

    fun addConfig(config: ReplyConfig, logInfo: (String) -> Unit = ::println): Boolean {
        if (!tempMode) {
            throw IllegalStateException("不可以在非 temp 模式添加配置")
        }
        // 不允许 messages 为空
        if (config.messages.isEmpty()) {
            logInfo("[${ReplyConfigCache::class.simpleName}] 错误: 包含空的 messages")
            return false
        }
        // 将所有的 simple reply 添加到 replies 里面
        config.simpleReplies.forEach {
            config.replies.add(ReplyData(replyText = it))
        }
        config.simpleReplies.clear()
        // 不允许空应答
        if (config.replies.isEmpty()) {
            logInfo("[${ReplyConfigCache::class.simpleName}] 错误: messages: ${config.messages} 没有配置应答")
            return false
        }
        // TODO 多 special 模式
        if (config.replies.any { it.specials.size > 1 }) {
            TODO("暂不支持多个 special")
        }
        var failure = false
        config.replies.forEach {
            try {
                it.bufferedImage = cacheFile(it.imageFile)
                it.bufferedVoice = cacheFile(it.voiceFile)
            } catch (e: Exception) {
                logInfo("[${AutoReplyCache::class.simpleName}] 错误: ${e.message}")
                failure = true
            }
        }
        if (failure) {
            return false
        }
        configList.add(config)
        return true
    }

    fun getReply(msg: String, user: User, group: Group?): ReplyData? = rwLock.read {
        val idx = indexMessageConfig[msg] ?: return null
        val config = configList[idx]
        if (config.needMembers.isNotEmpty()) {
            if (group == null) {
                return null
            }
            config.needMembers.forEach {
                if (group.getMember(it) == null) {
                    return null
                }
            }
        }
        if (config.specialReplies.containsKey(user.id)) {
            return config.specialReplies[user.id]!!.random()
        } else if (config.specialReplies.containsKey(AutoReplyCache.kDefaultSpecial)) {
            return config.specialReplies[AutoReplyCache.kDefaultSpecial]!!.random()
        }
        return null
    }
}
