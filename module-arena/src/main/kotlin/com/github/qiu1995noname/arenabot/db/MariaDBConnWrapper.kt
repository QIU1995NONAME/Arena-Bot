package com.github.qiu1995noname.arenabot.db

import java.sql.Connection
import java.sql.DriverManager

class MariaDBConnWrapper(
    host: String,
    port: Int = 3306,
    dbName: String,
    private val dbUser: String,
    private val dbPass: String,
    maxPoolSize: Int = 10,
    timeout: Int = 1000,
) : ConnWrapper {
    private val connStr = "jdbc:mariadb://${host}:${
        if (port <= 0) 3306 else port
    }/${dbName}?pool&maxPoolSize=${
        if (maxPoolSize < 6) 6 else maxPoolSize
    }&minPoolSize=6&connectTimeout=${
        if (timeout < 10) 10 else timeout
    }"

    /**
     * 使用数据库连接做点什么。连接用完了关不关无所谓的。
     *
     * 注意 连接的 autoCommit 属性已经被关掉了，对数据库的任何修改记得 Commit
     *
     * @param action 函数，用连接做点什么
     * @return       返回 action 的返回值
     */
    override fun <T> useConnection(action: Connection.() -> T?): T? {
        val conn = DriverManager.getConnection(connStr, dbUser, dbPass)
        conn.autoCommit = false
        try {
            return conn.action()
        } catch (t: Throwable) {
            // TODO new exception class
            throw Exception("Exception in action!", t)
        } finally {
            try {
                if (!conn.isClosed) {
                    conn.close()
                }
            } catch (t: Throwable) {
                // no-op
            }
        }
    }
}

