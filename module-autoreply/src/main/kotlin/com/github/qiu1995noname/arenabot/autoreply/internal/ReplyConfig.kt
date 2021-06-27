package com.github.qiu1995noname.arenabot.autoreply.internal

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * 用来描述一份"回复配置", 即：当收到xxx时，回复yyy
 *
 * @param messages       当接收到这些消息的时候
 * @param needMembers    仅群组生效 是否需要其它群原存在。缺省为空
 * @param simpleReplies  仅字符串的回复，没有特殊功能
 * @param replies        功能比较全的回复
 */
@Serializable
internal data class ReplyConfig(
        val messages: HashSet<String>,
        val needMembers: HashSet<Long> = HashSet(),
        val simpleReplies: HashSet<String> = HashSet(),
        val replies: ArrayList<@Contextual ReplyData> = ArrayList(),
) {
    /**
     * 此条消息是否包含特殊回复，属于索引功能，不包含在配置文件中
     */
    val specialReplies: HashMap<Long, ArrayList<@Contextual ReplyData>> = HashMap()

    /**
     * 深拷贝
     */
    fun clone(): ReplyConfig {
        val res = ReplyConfig(HashSet())
        this.messages.forEach {
            res.messages.add(it)
        }
        this.needMembers.forEach {
            res.needMembers.add(it)
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
