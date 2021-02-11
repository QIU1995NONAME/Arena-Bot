package com.github.qiu1995noname.arenabot.autoreply

import com.github.qiu1995noname.arenabot.autoreply.command.CommandReload
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

@Suppress("unused")
@AutoService(JvmPlugin::class)
object AutoReplyPlugin : KotlinPlugin(
    JvmPluginDescription(
        id = AutoReplyPlugin::class.java.name,
        version = AutoReplyPlugin::class.java.`package`.implementationVersion,
    ) {
        name(AutoReplyPlugin::class.java.name)
        author("...")
        info("...")
        dependsOn(WhitelistsPlugin.id)
    }
) {
    override fun PluginComponentStorage.onLoad() {
        logger.info("Version: " + AutoReplyPlugin.version)
    }

    override fun onEnable() {
        AutoReplyConfig.reload()
        GlobalEventChannel.registerListenerHost(AutoReplyEventHandlers)
        CommandReload.register()
    }

    override fun onDisable() {
        CommandReload.unregister()
    }
}
