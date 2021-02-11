package com.github.qiu1995noname.arenabot.whitelists.command

import com.github.qiu1995noname.arenabot.whitelists.WhitelistsConfig
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsPlugin
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand

object CommandManage : CompositeCommand(
    WhitelistsPlugin,
    "manage",
    description = "名单管理命令",
) {
    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.special() = WhitelistsConfig.withCheck(this, true) {
        val res = StringBuilder()
        res.append("特殊用户名单\n")
        WhitelistsConfig.grantedSets.specialUsers.forEach {
            res.append("$it\n")
        }
        sendMessage(res.toString())
    }

    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.group() = WhitelistsConfig.withCheck(this, true) {
        val res = StringBuilder()
        res.append("群组白名单\n")
        WhitelistsConfig.grantedSets.allowedGroups.forEach {
            res.append("$it\n")
        }
        sendMessage(res.toString())
    }

    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.user() = WhitelistsConfig.withCheck(this, true) {
        val res = StringBuilder()
        res.append("用户黑名单\n")
        WhitelistsConfig.grantedSets.bannedUsers.forEach {
            res.append("$it\n")
        }
        sendMessage(res.toString())
    }

    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.special(code: Long) = WhitelistsConfig.withCheck(this, true) {
        if (code > 0) {
            if (WhitelistsConfig.grantedSets.specialUsers.contains(code)) {
                sendMessage("用户 $code 已经存在于特殊名单")
            } else {
                WhitelistsConfig.grantedSets.specialUsers.add(code)
                sendMessage("用户 $code 已加入特殊名单")
            }
        } else {
            val code1 = -code
            if (!WhitelistsConfig.grantedSets.specialUsers.contains(code1)) {
                sendMessage("用户 $code1 不在特殊名单")
            } else {
                WhitelistsConfig.grantedSets.specialUsers.remove(code1)
                sendMessage("用户 $code1 已移除特殊名单")
            }
        }
    }

    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.group(code: Long) = WhitelistsConfig.withCheck(this, true) {
        if (code > 0) {
            if (WhitelistsConfig.grantedSets.allowedGroups.contains(code)) {
                sendMessage("群 $code 已经存在于白名单")
            } else {
                WhitelistsConfig.grantedSets.allowedGroups.add(code)
                sendMessage("群 $code 已加入白名单")
            }
        } else {
            val code1 = -code
            if (!WhitelistsConfig.grantedSets.allowedGroups.contains(code1)) {
                sendMessage("群 $code1 不在白名单")
            } else {
                WhitelistsConfig.grantedSets.allowedGroups.remove(code1)
                sendMessage("群 $code1 已移除白名单")
            }
        }
    }

    @Suppress("unused")
    @SubCommand
    suspend fun CommandSender.user(code: Long) = WhitelistsConfig.withCheck(this, true) {
        if (code > 0) {
            if (!WhitelistsConfig.grantedSets.bannedUsers.contains(code)) {
                sendMessage("用户 $code 不在黑名单")
            } else {
                WhitelistsConfig.grantedSets.bannedUsers.remove(code)
                sendMessage("用户 $code 已移除黑名单")
            }
        } else {
            val code1 = -code
            if (WhitelistsConfig.grantedSets.bannedUsers.contains(code1)) {
                sendMessage("用户 $code1 已在黑名单")
            } else {
                WhitelistsConfig.grantedSets.bannedUsers.add(code1)
                sendMessage("用户 $code1 已加入黑名单")
            }
        }
    }
}
