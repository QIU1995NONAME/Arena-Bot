package com.github.qiu1995noname.arenabot.whitelists

class WhitelistsCheckException : Exception {
    @Suppress("unused")
    constructor() : super()

    @Suppress("unused")
    constructor(message: String) : super(message)

    @Suppress("unused")
    constructor(message: String, cause: Throwable) : super(message, cause)

    @Suppress("unused")
    constructor(cause: Throwable) : super(cause)
}
