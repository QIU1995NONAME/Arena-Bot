package com.github.qiu1995noname.arenabot.whitelists

import com.github.qiu1995noname.arenabot.whitelists.command.CommandManage
import com.github.qiu1995noname.arenabot.whitelists.command.CommandSummary
import com.google.auto.service.AutoService
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.register
import net.mamoe.mirai.console.command.CommandManager.INSTANCE.unregister
import net.mamoe.mirai.console.extension.PluginComponentStorage
import net.mamoe.mirai.console.plugin.jvm.JvmPlugin
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.version
import net.mamoe.mirai.event.GlobalEventChannel

@Suppress("unused")
@AutoService(JvmPlugin::class)
object WhitelistsPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = WhitelistsPlugin::class.java.name,
        version = WhitelistsPlugin::class.java.`package`.implementationVersion,
    ) {
        name(WhitelistsPlugin::class.java.name)
        author("...")
        info("...")
    }
) {
    override fun PluginComponentStorage.onLoad() {
        logger.info("Version: " + WhitelistsPlugin.version)
    }

    override fun onEnable() {
        WhitelistsConfig.reload()
        WhitelistsData.reload()
        GlobalEventChannel.registerListenerHost(WhitelistsEventHandlers)
        CommandManage.register()
        CommandSummary.register()
    }

    override fun onDisable() {
        CommandManage.unregister()
        CommandSummary.unregister()
    }
}
