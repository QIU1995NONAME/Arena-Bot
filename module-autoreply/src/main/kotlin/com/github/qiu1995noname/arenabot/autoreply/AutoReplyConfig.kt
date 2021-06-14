package com.github.qiu1995noname.arenabot.autoreply

import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

object AutoReplyConfig : AutoSavePluginConfig(
        AutoReplyConfig::class.simpleName!!
) {
    @ValueDescription("本地配置文件夹，只关注这个文件夹里面的配置文件")
    val configFolder by value("replies")

    @ValueDescription("配置 Bot 被戳一戳时匹配的特殊值配置")
    val nudgedTag by value("BotNudgedEventDetected")

    @ValueDescription("重新加载配置时，是否忽略错误，建议仅在调试期间配置为 true")
    val reloadIgnoreFailure by value(false)

    @ValueDescription("从属的群组，只在这个群组的群文件中寻找配置文件，如果为负值，就不判断群组")
    val ownerGroup by value(706079559L)

    @ValueDescription("寻找群里面指定群文件夹里面的配置文件，若为空，寻找 Bot QQ号 为名字的文件夹")
    val remoteFolderName by value("")
}
