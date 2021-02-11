package com.github.qiu1995noname.arenabot.utils

import com.github.qiu1995noname.arenabot.db.ConnWrapper
import com.github.qiu1995noname.arenabot.entity.Cytus2Level
import com.github.qiu1995noname.arenabot.entity.Cytus2LevelSelector
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object ConnWrapperExt {
    fun ConnWrapper.getActors(): Map<String, List<String>> {
        val sql = "SELECT * FROM `cytus2-actor`;"
        val res = HashMap<String, List<String>>()
        this.useConnection {
            val stmt = this.createStatement()
            val rs = stmt.executeQuery(sql)
            while (rs.next()) {
                val id = rs.getString("id")
                val nicks = rs.getString("nicknames")

                @Suppress("RemoveExplicitTypeArguments")
                res[id] = Json { ignoreUnknownKeys = true }.decodeFromString<List<String>>(nicks)
            }
        }
        return res
    }

    fun ConnWrapper.selectCytus2Level(init: Cytus2LevelSelector.() -> Unit): Cytus2Level? {
        val s = Cytus2LevelSelector()
        s.init()
        return this.useConnection {
            val stmt = this.createStatement()
            val rs = stmt.executeQuery(s.buildSQL())
            if (!rs.next()) {
                return@useConnection null
            }
            return@useConnection Cytus2Level(
                name = rs.getString("name"),
                actor = rs.getString("actor_id"),
                difficulty = rs.getInt("difficulty"),
                type = Cytus2Level.Type.fromString(rs.getString("type")),
                free = rs.getBoolean("free"),
                deleted = rs.getBoolean("deleted"),
            )
        }
    }
}
