package com.github.qiu1995noname.arenabot.autoreply.internal

import kotlinx.serialization.Serializable

/**
 * 用来描述一条 回复配置 的类
 *
 * @param replyText 一条文字回复
 * @param specials  QQ号组，代表此条回复是针对这一组人的
 * @param atSender  此条回复是否需要 At 发送者。缺省为 false
 * @param imageFile 此条回复附带一张图，缺省为无图
 * @param voiceFile 此条回复附带音频，缺省为无音频
 */
@Serializable
internal data class ReplyData(
        val replyText: String,
        val specials: HashSet<Long> = HashSet(),
        val atSender: Boolean = false,
        val imageFile: String = "",
        val voiceFile: String = "",
) {
    var bufferedImage: ByteArray? = null
    var bufferedVoice: ByteArray? = null

    /**
     * 深拷贝
     */
    fun clone(): ReplyData {
        val res = ReplyData(
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
