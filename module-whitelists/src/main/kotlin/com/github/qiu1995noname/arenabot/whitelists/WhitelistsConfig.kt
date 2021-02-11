package com.github.qiu1995noname.arenabot.whitelists

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.containsFriend

object WhitelistsConfig : AutoSavePluginConfig(
    WhitelistsConfig::class.simpleName!!
) {
    /**
     * 名单列表
     * @param specialUsers   被特殊允许的用户名单
     * @param allowedGroups  群组白名单
     * @param bannedUsers    用户黑名单
     */
    @Serializable
    data class GrantedSets(
        val specialUsers: HashSet<Long> = HashSet(),
        val allowedGroups: HashSet<Long> = HashSet(),
        val bannedUsers: HashSet<Long> = HashSet(),
    )

    /**
     * 所有者 拥有名单的控制权
     */
    private val owner by value(292529702L)

    /**
     * 直接允许好友
     */
    private val allowFriend by value(true)

    /**
     * 名单列表
     */
    val grantedSets by value<GrantedSets>()

    /**
     * 校验用户
     *
     * @param user      某个用户
     * @param onlyOwner 是否仅 Owner 有权限
     */
    private fun checkUser(user: User, onlyOwner: Boolean = false): Boolean {
        // Bot Owner
        if (user.id == owner) {
            return true
        }
        // 接下来就不是 Owner 了
        if (onlyOwner) {
            return false
        }
        // 如果是被特许的
        if (user.id in grantedSets.specialUsers) {
            return true
        }
        // 如果被 Ban 则没有权限
        if (user.id in grantedSets.bannedUsers) {
            return false
        }
        // 如果是好友
        if (user.bot.containsFriend(user.id) && allowFriend) {
            return true
        }
        // 如果在群，或者由群发起的私聊，且群在白名单里面
        if (user is NormalMember && user.group.id in grantedSets.allowedGroups) {
            return true
        }
        // 否则 无权限
        return false
    }

    /**
     * 校验命令发起者
     *
     * @param sender    某个发起者
     * @param onlyOwner 是否仅 Owner 有权限
     */
    private fun checkCommandSender(sender: CommandSender, onlyOwner: Boolean = false): Boolean {
        // 控制台拥有最高权限
        if (sender is ConsoleCommandSender) {
            return true
        }
        return checkUser(sender.user!!, onlyOwner)
    }

    suspend fun <T> withCheck(sender: CommandSender, onlyOwner: Boolean = false, action: suspend () -> T): T {
        val caller = Thread.currentThread().stackTrace[2].toString()
        if (!checkCommandSender(sender, onlyOwner)) {
            val info = "Access Deny: ${sender.user} @ ${sender.subject} : $caller"
            WhitelistsPlugin.logger.verbose(info)
            throw WhitelistsCheckException(info)
        }
        return action()
    }

    suspend fun <T> withCheck(user: User, onlyOwner: Boolean = false, action: suspend () -> T): T {
        val caller = Thread.currentThread().stackTrace[2].toString()
        if (!checkUser(user, onlyOwner)) {
            val info = "Access Deny: $user : $caller"
            WhitelistsPlugin.logger.verbose(info)
            throw WhitelistsCheckException(info)
        }
        return action()
    }
}
