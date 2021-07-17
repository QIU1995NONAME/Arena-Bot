package com.github.qiu1995noname.arenabot.bot.command

import com.github.qiu1995noname.arenabot.bot.ArenaBotData
import com.github.qiu1995noname.arenabot.bot.ArenaBotPlugin
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig
import com.github.qiu1995noname.arenabot.whitelists.event.CustomChannelGroupMemberEvent
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.event.broadcast
import net.mamoe.mirai.message.data.MessageChain

object CommandArenaShutdown : RawCommand(
        ArenaBotPlugin,
        "arena-shutdown",
        "强制退出", "强制结束", "強制退出", "強制結束",
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) = WhitelistsConfig.withCheck(this, false) {
        if (this !is MemberCommandSender) {
            sendMessage("不支持在群外使用此功能")
            return@withCheck
        }
        ArenaBotData.shutdown(this.bot, this.group.id).filter { it.isNotEmpty() }.forEach {
            sendMessage(it)
        }
        // TODO 应该根据状态码来决定是否需要触发此事件
        CustomChannelGroupMemberEvent(this.user, "/ArenaBot/Event/GroupMember/ArenaShutdown").broadcast()
    }
}
