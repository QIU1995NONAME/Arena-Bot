package com.github.qiu1995noname.arenabot.autoreply

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object AutoReplyConfig : AutoSavePluginConfig(
    AutoReplyConfig::class.simpleName!!
) {
    val configFolder by value("replies")
    val nudgedTag by value("BotNudgedEventDetected")
    val reloadIgnoreFailure by value(false)
}
