package com.github.qiu1995noname.arenabot.bot

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value

object ArenaBotConfig : AutoSavePluginConfig(
    "ArenaBotConfig"
) {
    @Serializable
    data class DatabaseConfig(
        val host: String = "localhost",
        val port: Int = 13306,
        val dbName: String = "arena-data-dev",
        val dbUser: String = "arena-bot",
        val dbPass: String = "arena-secret",
    )

    val database by value<DatabaseConfig>()
}
