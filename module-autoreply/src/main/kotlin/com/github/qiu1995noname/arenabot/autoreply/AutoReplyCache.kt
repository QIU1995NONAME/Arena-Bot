package com.github.qiu1995noname.arenabot.autoreply

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.ByteArrayInputStream
import java.io.File
import java.security.MessageDigest
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.collections.List
import kotlin.collections.any
import kotlin.collections.first
import kotlin.collections.forEach
import kotlin.collections.forEachIndexed
import kotlin.collections.isNotEmpty
import kotlin.collections.random
import kotlin.collections.set
import kotlin.concurrent.read
import kotlin.concurrent.write

object AutoReplyCache {
    private const val kDefaultSpecial = -1L

    /**
     * @param messages       当接收到这些消息的时候
     * @param simpleReplies  仅字符串的回复，没有特殊功能
     * @param replies        功能比较全的回复
     */
    @Serializable
    data class Config(
        val messages: HashSet<String>,
        val simpleReplies: HashSet<String> = HashSet(),
        val replies: ArrayList<@Contextual Reply> = ArrayList(),
    ) {
        /**
         * 此条消息是否包含特殊回复，属于索引功能，不包含在配置文件中
         */
        val specialReplies: HashMap<Long, ArrayList<@Contextual Reply>> = HashMap()

        /**
         * @param replyText 一条文字回复
         * @param specials  QQ号组，代表此条回复是针对这一组人的
         * @param atSender  此条回复是否需要 At 发送者。缺省为 false
         * @param imageFile 此条回复附带一张图，缺省为无图
         * @param voiceFile 此条回复附带音频，缺省为无音频
         */
        @Serializable
        data class Reply(
            val replyText: String,
            val specials: HashSet<Long> = HashSet(),
            val atSender: Boolean = false,
            val imageFile: String = "",
            val voiceFile: String = "",
        ) {
            var bufferedImage: ByteArray? = null
            var bufferedVoice: ByteArray? = null
            fun clone(): Reply {
                val res = Reply(
                    this.replyText,
                    atSender = this.atSender,
                    imageFile = this.imageFile,
                    voiceFile = this.voiceFile,
                )
                this.specials.forEach {
                    res.specials.add(it)
                }
                return res
            }
        }

        fun clone(): Config {
            val res = Config(HashSet())
            this.messages.forEach {
                res.messages.add(it)
            }
            this.simpleReplies.forEach {
                res.simpleReplies.add(it)
            }
            this.replies.forEach {
                res.replies.add(it.clone())
            }
            return res
        }
    }

    internal class ConfigCache(
        val tempMode: Boolean = false
    ) {
        private val rwLock = ReentrantReadWriteLock()
        private val configList = ArrayList<Config>()
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

        fun moveFrom(cache: ConfigCache) = rwLock.write {
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
                    var key = kDefaultSpecial
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

        fun addConfig(config: Config, logInfo: (String) -> Unit = ::println): Boolean {
            if (!tempMode) {
                throw IllegalStateException("不可以在非 temp 模式添加配置")
            }
            // 不允许 messages 为空
            if (config.messages.isEmpty()) {
                logInfo("[${AutoReplyCache::class.simpleName}] 错误: 包含空的 messages")
                return false
            }
            // 将所有的 simple reply 添加到 replies 里面
            config.simpleReplies.forEach {
                config.replies.add(Config.Reply(replyText = it))
            }
            config.simpleReplies.clear()
            // 不允许空应答
            if (config.replies.isEmpty()) {
                logInfo("[${AutoReplyCache::class.simpleName}] 错误: messages: ${config.messages} 没有配置应答")
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

        fun getReply(msg: String, user: User): Config.Reply? = rwLock.read {
            val idx = indexMessageConfig[msg] ?: return null
            val config = configList[idx]
            if (config.specialReplies.containsKey(user.id)) {
                return config.specialReplies[user.id]!!.random()
            } else if (config.specialReplies.containsKey(kDefaultSpecial)) {
                return config.specialReplies[kDefaultSpecial]!!.random()
            }
            return null
        }
    }

    private val cache = ConfigCache(tempMode = false)

    suspend fun getReply(msg: String, user: User, contact: Contact): MessageChain {
        val reply = cache.getReply(msg, user)
        val builder = MessageChainBuilder()
        if (reply == null) {
            return builder.build()
        }
        if (reply.atSender && user is Member) {
            builder.add(At(user))
            builder.add("\n")
        }
        builder.add(reply.replyText)
        if (reply.bufferedImage != null) {
            builder.add("\n")
            builder.add(ByteArrayInputStream(reply.bufferedImage).uploadAsImage(contact))
        }
        // TODO Wait for Mirai upgrading
        if (reply.bufferedVoice != null && contact is Group) {
            reply.bufferedVoice!!.toExternalResource().use {
                // TODO
                //     cook it
                //     目前就直接发出去了
                contact.sendMessage(contact.uploadVoice(it))
            }
        }
        return builder.build()
    }

    /**
     *  从指定的文件夹中加载所有配置文件并替换现有配置
     *
     *  @param filePath      配置文件所在的文件夹
     *  @param ignoreFailure 是否忽略解析失败的配置文件
     *  @param logInfo       消息回调
     *  @return
     */
    fun reload(
        filePath: String,
        ignoreFailure: Boolean = false,
        logInfo: (String) -> Unit = ::println
    ): Boolean {
        val folder = File(filePath)
        if (!folder.exists()) {
            logInfo("[${AutoReplyCache::class.simpleName}] '$filePath' 不存在！")
            return false
        }
        if (!folder.isDirectory) {
            logInfo("[${AutoReplyCache::class.simpleName}] '$filePath' 不是一个文件夹！")
            return false
        }
        var failure = false
        val configCache = ConfigCache(tempMode = true)

        folder.walk().filter {
            it.isFile && (it.name.endsWith(".json", true) || it.name.endsWith(".json.txt", true))
        }.forEach { file ->
            logInfo("正在解析配置文件: \"$file\"")
            val content = file.readText()
            try {
                Json { ignoreUnknownKeys = true }.decodeFromString<List<Config>>(content).forEach {
                    if (!configCache.addConfig(it.clone(), logInfo)) {
                        failure = true
                    }
                }
            } catch (e: Exception) {
                logInfo("[${AutoReplyCache::class.simpleName}] 解析文件 $file 失败: ${e.message}")
                failure = true
            }
        }
        if (failure && !ignoreFailure) {
            return false
        }
        cache.moveFrom(configCache)
        return true
    }
}
