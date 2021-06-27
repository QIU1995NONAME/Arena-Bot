package com.github.qiu1995noname.arenabot.autoreply

import com.github.qiu1995noname.arenabot.autoreply.internal.ReplyConfig
import com.github.qiu1995noname.arenabot.autoreply.internal.ReplyConfigCache
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.*
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.MessageChainBuilder
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import java.io.ByteArrayInputStream
import java.io.File

object AutoReplyCache {
    const val kDefaultSpecial = -1L
    private val cache = ReplyConfigCache(tempMode = false)

    suspend fun getReply(msg: String, user: User, contact: Contact): MessageChain {
        val reply = cache.getReply(msg, user,
                if (contact is Group) contact else null
        )
        val builder = MessageChainBuilder()
        if (reply == null) {
            return builder.build()
        }
        if (reply.atSender && user is Member) {
            builder.add(At(user))
            builder.add("\n")
        }
        if (contact is Group) {
            reply.atOthers.forEach {
                val mem = contact.getMember(it)
                if (mem != null) {
                    builder.add(At(mem))
                }
            }
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
        val configCache = ReplyConfigCache(tempMode = true)

        folder.walk().filter {
            it.isFile && (it.name.endsWith(".json", true) || it.name.endsWith(".json.txt", true))
        }.forEach { file ->
            logInfo("正在解析配置文件: \"$file\"")
            val content = file.readText()
            try {
                Json { ignoreUnknownKeys = true }.decodeFromString<List<ReplyConfig>>(content).forEach {
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
