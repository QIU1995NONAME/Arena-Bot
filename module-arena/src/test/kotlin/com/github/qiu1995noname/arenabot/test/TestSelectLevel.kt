package com.github.qiu1995noname.arenabot.test

import com.github.qiu1995noname.arenabot.db.MariaDBConnWrapper
import com.github.qiu1995noname.arenabot.entity.Cytus2Level
import com.github.qiu1995noname.arenabot.utils.ConnWrapperExt.selectCytus2Level
import org.junit.Assert
import org.junit.Test

class TestSelectLevel {
    @Test
    fun testSelectCytus2Level() {
        val dbw = MariaDBConnWrapper(
                host = "127.0.0.1",
                port = 13306,
                dbName = "arena-data-dev",
                dbUser = "arena-bot",
                dbPass = "arena-secret",
        )
        repeat(100) {
            val level = dbw.selectCytus2Level {}
            Assert.assertNotNull(level);
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { freeOnly = true }
            Assert.assertTrue(level != null && level.free)
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { minDifficulty = 800 }
            Assert.assertTrue(level != null && level.difficulty >= 800)
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { maxDifficulty = 1200 }
            Assert.assertTrue(level != null && level.difficulty <= 1200)
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { type = Cytus2Level.Type.HARD }
            Assert.assertTrue(level != null && level.type == Cytus2Level.Type.HARD)
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { type = Cytus2Level.Type.CHAOS }
            Assert.assertTrue(level != null && level.type == Cytus2Level.Type.CHAOS)
        }
        repeat(100) {
            val level = dbw.selectCytus2Level { type = Cytus2Level.Type.GLITCH }
            Assert.assertTrue(level != null && level.type == Cytus2Level.Type.GLITCH)
        }
    }
}
