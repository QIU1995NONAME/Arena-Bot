package com.github.qiu1995noname.arenabot.whitelists.event

import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.AbstractEvent
import net.mamoe.mirai.event.events.GroupMemberEvent

/**
 *  这是一个自定义的 Channel Event 用来在各个模块之间联动。
 *
 *  定义为：在 member 所在的群中 member 触发了一个名为 channel 的事件
 */
class CustomChannelGroupMemberEvent(
        override val member: Member,
        val channel: String
) : AbstractEvent(), GroupMemberEvent
