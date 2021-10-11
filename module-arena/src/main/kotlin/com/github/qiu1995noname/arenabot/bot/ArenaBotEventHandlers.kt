package com.github.qiu1995noname.arenabot.bot

import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig.check
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import java.util.regex.Pattern

object ArenaBotEventHandlers : ListenerHost {
    @Suppress("unused")
    @EventHandler
    suspend fun tryParseScore(event: GroupMessageEvent) {
        if (event.message.size != 2) {
            return
        }
        if (event.message[1] !is PlainText) {
            return
        }
        val msg = event.message[1].toString()
        if (!Pattern.matches("/[0-9]+(\\.[0-9]*)?", msg)) {
            return
        }
        //============================================================
        event.sender.check(false)
        var score = msg.substring(1, msg.length).toDouble()
        while (score > 100.0000001) {
            score /= 10
        }
        ArenaBotData.uploadScore(event.bot, event.group.id, event.sender.id, score)
                .filter { it.isNotEmpty() }.forEach {
                    event.subject.sendMessage(it)
                }
    }
}

