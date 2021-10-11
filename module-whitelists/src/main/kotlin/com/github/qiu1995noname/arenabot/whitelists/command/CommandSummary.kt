package com.github.qiu1995noname.arenabot.whitelists.command

import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig.check
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsData
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsPlugin
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText

object CommandSummary : RawCommand(
        WhitelistsPlugin, "summary",
        description = "获得各模块使用频率"
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {
        this.check(true)
        val days = args.filterIsInstance<PlainText>().firstOrNull()?.content?.toIntOrNull()
        sendMessage(WhitelistsData.summary(this.bot, days ?: 7))
    }
}
