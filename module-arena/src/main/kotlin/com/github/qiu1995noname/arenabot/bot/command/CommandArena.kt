package com.github.qiu1995noname.arenabot.bot.command

import com.github.qiu1995noname.arenabot.bot.ArenaBotData
import com.github.qiu1995noname.arenabot.bot.ArenaBotPlugin
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.message.data.MessageChain

object CommandArena : RawCommand(
    ArenaBotPlugin,
    "arena",
    "加入竞技", "竞技",
    description = "启动/加入竞技模式",
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) = WhitelistsConfig.withCheck(this, false) {
        if (this !is MemberCommandSender) {
            sendMessage("不支持在群外使用此功能")
            return@withCheck
        }
        sendMessage(ArenaBotData.createOrJoin(this.bot, this.group.id, this.user.id))
    }
}
