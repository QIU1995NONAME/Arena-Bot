package com.github.qiu1995noname.arenabot.autoreply.command

import com.github.qiu1995noname.arenabot.autoreply.AutoReplyCache
import com.github.qiu1995noname.arenabot.autoreply.AutoReplyConfig
import com.github.qiu1995noname.arenabot.autoreply.AutoReplyPlugin
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig.check
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.RawCommand
import net.mamoe.mirai.message.data.MessageChain

object CommandReload : RawCommand(
        AutoReplyPlugin,
        "reload",
        description = "重新加载自动回复配置",
) {
    override suspend fun CommandSender.onCommand(args: MessageChain) {
        this.check(true)
        // TODO 不要让两次重新加载冲突
        sendMessage("重新加载中...")
        try {
            val res = AutoReplyCache.reload(AutoReplyConfig.configFolder, AutoReplyConfig.reloadIgnoreFailure)
            if (!res) {
                sendMessage("重新加载失败，请查看日志")
            } else {
                sendMessage("重新加载成功")
            }
        } catch (t: Throwable) {
            AutoReplyPlugin.logger.warning(t)
            sendMessage("重新加载失败，未捕获的异常，请查看日志")
        }
    }
}
