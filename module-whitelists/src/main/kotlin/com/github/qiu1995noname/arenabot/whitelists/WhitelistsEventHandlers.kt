package com.github.qiu1995noname.arenabot.whitelists

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.ListenerHost
import net.mamoe.mirai.event.events.BotInvitedJoinGroupRequestEvent

object WhitelistsEventHandlers : ListenerHost {
    @Suppress("unused")
    @EventHandler
    suspend fun onJoin(event: BotInvitedJoinGroupRequestEvent) {
        if (event.groupId in WhitelistsConfig.grantedSets.allowedGroups) {
            WhitelistsPlugin.logger.info("群(${event.groupId}): ${event.groupName} 在白名单 已自动加群")
            event.accept()
        } else {
            WhitelistsPlugin.logger.info("群(${event.groupId}): ${event.groupName} 不在白名单 已忽略")
            event.ignore()
        }
    }
}

