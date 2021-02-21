package com.github.qiu1995noname.arenabot.entity

import kotlinx.serialization.Serializable

@Serializable
data class Cytus2Level(
    val name: String,
    val actor: String,
    val difficulty: Int,
    val type: Type,
    val free: Boolean = false,
    val deleted: Boolean = false,
) {
    enum class Type {
        UNKNOWN, HARD, CHAOS, GLITCH, ;

        companion object {
            @JvmStatic
            fun fromString(str: String): Type = when (str) {
                "HARD" -> HARD
                "CHAOS" -> CHAOS
                "GLITCH" -> GLITCH
                else -> UNKNOWN
            }
        }
    }

    override fun toString(): String {
        return "Cytus2 Level: { " +
                "name: '$name', " +
                "actor: '$actor', " +
                String.format("difficulty: '%.2f', ", difficulty * 1e-2) +
                "type: '$type' " +
                "free?: $free" +
                "deleted?: $deleted" +
                "}"
    }

    fun toStringWithNewLine(): String {
        return "Cytus2 Level: \n" +
                "  曲名: '$name'\n" +
                "  角色: '$actor'\n" +
                String.format("  难度: '%.2f'\n", difficulty * 1e-2) +
                "  类型: '$type' \n" +
                (if (free && type != Type.GLITCH) "  [免费] \n" else "") +
                (if (deleted) "  [已删除] \n" else "")
    }
}
