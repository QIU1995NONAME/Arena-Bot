package com.github.qiu1995noname.arenabot.bot.command

import com.github.qiu1995noname.arenabot.bot.ArenaBotPlugin
import com.github.qiu1995noname.arenabot.utils.ArenaManager
import com.github.qiu1995noname.arenabot.utils.ConnWrapperExt.selectCytus2Level
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText

object CommandSongSelect : RawCommand(
    ArenaBotPlugin,
    "select",
    "抽歌",
    description = "抽歌命令",
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) = WhitelistsConfig.withCheck(this, false) {
        try {
            var unparsed = ""
            val level = ArenaBotPlugin.connWrapper.selectCytus2Level {
                args.filterIsInstance<PlainText>().forEach {
                    if (!this.parseArgument(it.toString())) {
                        unparsed += "$it "
                    }
                }
            }
            if (unparsed.isNotBlank()) {
                unparsed = "\n(未解析的参数: '${unparsed}'"
            }
            if (level == null) {
                sendMessage("没有抽到歌曲 T_T $unparsed")
            } else {
                if (this !is MemberCommandSender) {
                    sendMessage(level.toStringWithNewLine() + unparsed)
                } else {
                    val tag = ArenaManager.trySetLevel(this.group.id, this.user, level)
                    sendMessage(tag + "\n" + level.toStringWithNewLine() + unparsed)
                }
            }
            return@withCheck
        } catch (t: Throwable) {
            sendMessage("内部异常 T_T，请报告 BUG")
            ArenaBotPlugin.logger.error(t)
        }
    }
}
