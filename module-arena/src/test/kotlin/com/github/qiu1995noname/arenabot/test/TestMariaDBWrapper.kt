package com.github.qiu1995noname.arenabot.test

import com.github.qiu1995noname.arenabot.db.MariaDBConnWrapper
import org.junit.Test
import java.util.*

class TestMariaDBWrapper {
    private val dbWrapper = MariaDBConnWrapper(
            host = "127.0.0.1",
            port = 13306,
            dbName = "arena-data-dev",
            dbUser = "arena-bot",
            dbPass = "arena-secret"
    )

    @Test
    fun testConnectionWrapperWithException() {
        repeat(10000) {
            try {
                dbWrapper.useConnection {
                    throw Exception("Test")
                }
            } catch (e: Exception) {
                // no-op
            }
        }
    }

    @Test
    fun testConnectionWrapper() {
        var costAction = 0L;
        repeat(10000) {
            dbWrapper.useConnection {
                val bgn = System.nanoTime()
                val li = LinkedList<String>()
                val stmt = this.createStatement()
                val rs = stmt.executeQuery("SHOW databases")
                while (rs.next()) {
                    li.addLast(rs.getString(1));
                }
                costAction += System.nanoTime() - bgn
            }
        }
        println("COST(Action): ${costAction / 1e9}")
    }
}

