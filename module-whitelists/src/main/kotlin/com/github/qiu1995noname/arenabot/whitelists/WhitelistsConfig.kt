package com.github.qiu1995noname.arenabot.whitelists

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.ConsoleCommandSender
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value
import net.mamoe.mirai.contact.NormalMember
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.containsFriend

object WhitelistsConfig : AutoSavePluginConfig(
        WhitelistsConfig::class.simpleName!!
) {
    @Serializable
    data class GrantedSets(
            @ValueDescription("被特殊允许的用户名单")
            val specialUsers: HashSet<Long> = HashSet(),
            @ValueDescription("群组白名单")
            val allowedGroups: HashSet<Long> = HashSet(setOf(706079559L)),
            @ValueDescription("用户黑名单")
            val bannedUsers: HashSet<Long> = HashSet(),
    )

    @ValueDescription("所有者 拥有名单的控制权")
    private val owner by value(292529702L)

    @ValueDescription("是否直接允许好友")
    private val allowFriend by value(true)

    @ValueDescription("名单列表")
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
     * 校验用户
     *
     * @param onlyOwner 是否仅 Owner 有权限
     */
    fun User.check(onlyOwner: Boolean = false) {
        val caller = Thread.currentThread().stackTrace[2].toString()
        if (!checkUser(this, onlyOwner)) {
            val info = "Access Deny: $this : $caller"
            WhitelistsPlugin.logger.verbose(info)
            throw WhitelistsCheckException(info)
        }
    }

    /**
     * 校验命令发起者
     *
     * @param onlyOwner 是否仅 Owner 有权限
     */
    fun CommandSender.check(onlyOwner: Boolean = false) {
        // 控制台拥有最高权限
        if (this is ConsoleCommandSender) {
            return
        }
        val caller = Thread.currentThread().stackTrace[2].toString()
        if (!checkUser(this.user!!, onlyOwner)) {
            val info = "Access Deny: ${this.user!!} : $caller"
            WhitelistsPlugin.logger.verbose(info)
            throw WhitelistsCheckException(info)
        }
    }
}
