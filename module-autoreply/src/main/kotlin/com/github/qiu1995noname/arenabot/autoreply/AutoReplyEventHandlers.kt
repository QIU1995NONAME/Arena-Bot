package com.github.qiu1995noname.arenabot.autoreply

import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig.check
import com.github.qiu1995noname.arenabot.whitelists.event.CustomChannelGroupMemberEvent
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.MessageEvent
import net.mamoe.mirai.event.events.NudgeEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.MiraiExperimentalApi

object AutoReplyEventHandlers : ListenerHost {
    @MiraiExperimentalApi
    @Suppress("unused")
    @EventHandler
    suspend fun onNudge(event: NudgeEvent) {
        if (event.target != event.bot) {
            return
        }
        val from = event.from
        if (from !is User) {
            return
        }
        from.check(false)
        val reply = AutoReplyCache.getReply(AutoReplyConfig.nudgedTag, from, event.subject)
        if (!reply.isEmpty()) {
            event.subject.sendMessage(reply)
        }
    }

    @Suppress("unused")
    @EventHandler
    suspend fun tryAutoReply(event: MessageEvent) {
        if (event.message.size != 2) {
            return
        }
        if (event.message[1] !is PlainText) {
            return
        }
        val msg = event.message[1].toString()

        if (msg == AutoReplyConfig.nudgedTag) {
            return
        }
        AutoReplyConfig.configSets.blockPrefix.forEach {
            if (msg.startsWith(it)) {
                return
            }
        }
        event.sender.check(false)
        val replyMessage = AutoReplyCache.getReply(msg, event.sender, event.subject)
        if (!replyMessage.isEmpty()) {
            event.subject.sendMessage(replyMessage)
        }
    }

    @Suppress("unused")
    @EventHandler
    suspend fun onCustomChannel(event: CustomChannelGroupMemberEvent) {
        // 这里没有进行权限配置
        AutoReplyPlugin.logger.verbose(
                "[CustomChannel] \"${event.channel}\" in ${event.group} by ${event.user}"
        )
        val replyMessage = AutoReplyCache.getReply(event.channel, event.user, event.group)
        if (!replyMessage.isEmpty()) {
            event.group.sendMessage(replyMessage)
        }
    }
}
