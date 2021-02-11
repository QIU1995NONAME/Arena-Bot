package com.github.qiu1995noname.arenabot.autoreply

import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig
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
        WhitelistsConfig.withCheck(from, false) {
            val reply = AutoReplyCache.getReply(AutoReplyConfig.nudgedTag, from, event.subject)
            if (!reply.isEmpty()) {
                event.subject.sendMessage(reply)
            }
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

        WhitelistsConfig.withCheck(event.sender, false) {
            val replyMessage = AutoReplyCache.getReply(msg, event.sender, event.subject)
            if (!replyMessage.isEmpty()) {
                event.subject.sendMessage(replyMessage)
            }
        }
    }
}
