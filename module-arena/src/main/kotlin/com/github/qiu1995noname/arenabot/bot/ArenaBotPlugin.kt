package com.github.qiu1995noname.arenabot.bot

import com.github.qiu1995noname.arenabot.bot.command.*
import com.github.qiu1995noname.arenabot.db.ConnWrapper
import com.github.qiu1995noname.arenabot.db.MariaDBConnWrapper
import com.github.qiu1995noname.arenabot.entity.Cytus2Actors
import com.github.qiu1995noname.arenabot.utils.ConnWrapperExt.getActors
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsPlugin
import com.google.auto.service.AutoService
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.id
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.version
import net.mamoe.mirai.event.GlobalEventChannel

@AutoService(JvmPlugin::class)
object ArenaBotPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = ArenaBotPlugin::class.java.name,
        version = ArenaBotPlugin::class.java.`package`.implementationVersion,
    ) {
        name(ArenaBotPlugin::class.java.name)
        author("...")
        info("...")
        dependsOn(WhitelistsPlugin.id)
    }
) {

    val connWrapper: ConnWrapper by lazy {
        MariaDBConnWrapper(
            host = ArenaBotConfig.database.host,
            port = ArenaBotConfig.database.port,
            dbName = ArenaBotConfig.database.dbName,
            dbUser = ArenaBotConfig.database.dbUser,
            dbPass = ArenaBotConfig.database.dbPass,
        )
    }

    override fun PluginComponentStorage.onLoad() {
        Class.forName("org.mariadb.jdbc.Driver")
        logger.info("Version: " + ArenaBotPlugin.version)
    }

    override fun onEnable() {
        ArenaBotConfig.reload()
        ArenaBotData.reload()
        Cytus2Actors.loadActors(connWrapper.getActors())
        GlobalEventChannel.registerListenerHost(ArenaBotEventHandlers)
        CommandSongSelect.register()
        CommandArena.register()
        CommandArenaStatus.register()
        CommandArenaShutdown.register()
        CommandArenaExit.register()
        CommandCyc.register()
    }

    override fun onDisable() {
        CommandSongSelect.unregister()
        CommandArena.unregister()
        CommandArenaStatus.unregister()
        CommandArenaShutdown.unregister()
        CommandArenaExit.unregister()
        CommandCyc.unregister()
    }
}
