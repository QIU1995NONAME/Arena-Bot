package com.github.qiu1995noname.arenabot.db

import java.sql.Connection

interface ConnWrapper {
    /**
     * 使用 connection 做点什么。
     *
     * 实现者的要求是，当调用时或调用前，为 action 准备一个 connection，同时尽可能让调用者为所欲为，而不出现严重的故障。
     *
     * 调用者的要求是，只需要关心 action 的动作，以及使用 connection，不需要考虑 connection 本身的处理
     *
     * @param        action    一个函数
     * @return                 [action] 返回什么就返回什么
     * @exception    Throwable [action] 抛什么异常就抛什么异常
     */
    fun <T> useConnection(action: Connection.() -> T?): T?
}
