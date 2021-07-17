package com.github.qiu1995noname.arenabot.entity

import java.util.regex.Pattern

class Cytus2LevelSelector {
    var minDifficulty: Int = 0
    var maxDifficulty: Int = Int.MAX_VALUE
    var type: Cytus2Level.Type? = null
    var freeOnly: Boolean = false
    var includeDeleted: Boolean = false
    var actor: String? = null

    fun buildSQL(): String {
        var sql = """
            SELECT *
            FROM (
                     SELECT *,
                            difficulty_hard AS difficulty,
                            'HARD'          AS type
                     FROM `cytus2-level`
                     UNION
                     SELECT *,
                            difficulty_chaos AS difficulty,
                            'CHAOS'          AS type
                     FROM `cytus2-level`
                     UNION
                     SELECT *,
                            difficulty_glitch AS difficulty,
                            'GLITCH'          AS type
                     FROM `cytus2-level`
                     WHERE difficulty_glitch IS NOT NULL
                 ) AS t1
             WHERE difficulty >= $minDifficulty
               AND difficulty <= $maxDifficulty
        """.trimIndent()
        when (type) {
            Cytus2Level.Type.HARD ->
                sql += " AND type = 'HARD' "
            Cytus2Level.Type.CHAOS ->
                sql += " AND type = 'CHAOS' "
            Cytus2Level.Type.GLITCH ->
                sql += " AND type = 'GLITCH' "
            else -> Unit
        }
        if (freeOnly) {
            sql += " AND free = 1 AND type != 'GLITCH' "
        }
        if (includeDeleted) {
            sql += " AND deleted = 1 "
        }
        if (actor != null) {
            sql += " AND actor_id = '$actor' "
        }
        sql += " ORDER BY RAND() LIMIT 1;"
        return sql
    }

    fun parseArgument(str: String): Boolean {
        if (Pattern.matches("[0-9]+(\\.[0-9]*)?\\+", str)) {
            // 13.5+ 12+ 9+
            minDifficulty = (str.substring(0, str.length - 1).toDouble() * 100).toInt()
        } else if (Pattern.matches("[0-9]+(\\.[0-9]*)?-", str)) {
            // 13.5- 11- 8-
            maxDifficulty = (str.substring(0, str.length - 1).toDouble() * 100).toInt()
        } else if (Pattern.matches("[0-9]+(\\.[0-9]*)?", str)) {
            // 7 9 12.5
            maxDifficulty = (str.toDouble() * 100 + 50).toInt()
            minDifficulty = maxDifficulty - 100
        } else if (
                Pattern.matches("HARD", str.toUpperCase()) ||
                Pattern.matches("困难", str) ||
                Pattern.matches("困難", str)
        ) {
            type = Cytus2Level.Type.HARD
        } else if (
                Pattern.matches("CHAOS", str.toUpperCase()) ||
                Pattern.matches("混沌", str)
        ) {
            type = Cytus2Level.Type.CHAOS
        } else if (
                Pattern.matches("GLITCH", str.toUpperCase()) ||
                Pattern.matches("混乱", str) ||
                Pattern.matches("混亂", str)
        ) {
            type = Cytus2Level.Type.GLITCH
        } else if (
                Pattern.matches("FREE", str.toUpperCase()) ||
                Pattern.matches("免费", str) ||
                Pattern.matches("免費", str)
        ) {
            freeOnly = true
        } else if (
                Pattern.matches("DELETED", str.toUpperCase()) ||
                Pattern.matches("已移除", str) ||
                Pattern.matches("已刪除", str)
        ) {
            includeDeleted = true
        } else if (Cytus2Actors.getActorName(str) != null) {
            actor = Cytus2Actors.getActorName(str)
        } else {
            return false
        }
        return true
    }
}
