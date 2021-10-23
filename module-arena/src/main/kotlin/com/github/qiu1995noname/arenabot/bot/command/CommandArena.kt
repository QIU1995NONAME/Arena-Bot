package com.github.qiu1995noname.arenabot.bot.command

import com.github.qiu1995noname.arenabot.bot.ArenaBotData
import com.github.qiu1995noname.arenabot.bot.ArenaBotPlugin
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig.check
import com.github.qiu1995noname.arenabot.whitelists.event.CustomChannelGroupMemberEvent
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.event.broadcast
import net.mamoe.mirai.message.data.MessageChain

object CommandArena : RawCommand(
        ArenaBotPlugin,
        "arena",
        "加入竞技", "竞技", "击剑", "競技", "擊劍", "加入競技", "\uD83E\uDD3A",
        description = "启动/加入竞技模式",
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {
        this.check(false)
        if (this !is MemberCommandSender) {
            sendMessage("不支持在群外使用此功能")
            return
        }
        sendMessage(ArenaBotData.createOrJoin(this.bot, this.group.id, this.user.id))
        // TODO 应该根据状态码来决定是否需要触发此事件
        CustomChannelGroupMemberEvent(this.user, "/ArenaBot/Event/GroupMember/ArenaJoin").broadcast()
    }
}
