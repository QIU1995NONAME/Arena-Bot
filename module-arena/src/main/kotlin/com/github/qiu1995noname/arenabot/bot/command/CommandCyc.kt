package com.github.qiu1995noname.arenabot.bot.command

import com.github.qiu1995noname.arenabot.bot.ArenaBotPlugin
import com.github.qiu1995noname.arenabot.whitelists.WhitelistsData
import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.GroupAwareCommandSender
import net.mamoe.mirai.console.command.MemberCommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.event.events.GroupMemberEvent
import kotlin.math.abs
import kotlin.math.floor

object CommandCyc : SimpleCommand(
        ArenaBotPlugin,
        "cyc",
        description = "Cytus 小P计算器",
) {
    private fun process(tp: Double, perfect: Int, good: Int, bad: Int, miss: Int): String {
        // TP 计算器： TP = (Perfect + 0.7 Great + 0.3 Good) / ALL
        if (tp > 100.000000001) {
            return "TP值异常，不支持100%以上"
        }
        if (tp < 90) {
            // TODO broadcast an event
            return "请不要越级！"
        }
        val all: Int = perfect + good + bad + miss
        if (perfect < 0 || good < 0 || bad < 0 || miss < 0 || all < 1) {
            return "错误的参数值！"
        }
        // 只包含大小P的tp
        val great: Double = 1.0 / 0.3 * (1.0 * perfect - (tp / 100 * all - 0.3 * good))
        val greatActual: Int = floor(great + 0.5).toInt()
        val perfectActual: Int = perfect - greatActual
        val tpActual: Double = 100.0 * (1.0 * perfectActual + 0.7 * greatActual + 0.3 * good) / all
        val tpDiff = abs(tp - tpActual)
        return "$perfectActual / $greatActual / $good / $bad / $miss\nTP: ${String.format("%.4f", tpActual)}" +
                (if (tpDiff > 0.1) "\n过大的TP偏差: ${String.format("%.4f", tpDiff)}" else "") +
                (if (greatActual < 0) "\n不可能的游戏结果" else "")
    }

    @Suppress("unused")
    @Handler
    suspend fun CommandSender.onCommand(tp: Double, perfect: Int) {
        if (this is MemberCommandSender) {
            WhitelistsData.sampling(WhitelistsData.Feature.CYC, this.group.id)
        }
        sendMessage(process(tp, perfect, 0, 0, 0))
    }

    @Suppress("unused")
    @Handler
    suspend fun CommandSender.onCommand(tp: Double, perfect: Int, good: Int) {
        if (this is MemberCommandSender) {
            WhitelistsData.sampling(WhitelistsData.Feature.CYC, this.group.id)
        }
        sendMessage(process(tp, perfect, good, 0, 0))
    }

    @Suppress("unused")
    @Handler
    suspend fun CommandSender.onCommand(tp: Double, perfect: Int, good: Int, bad: Int, miss: Int) {
        if (this is MemberCommandSender) {
            WhitelistsData.sampling(WhitelistsData.Feature.CYC, this.group.id)
        }
        sendMessage(process(tp, perfect, good, bad, miss))
    }
}
