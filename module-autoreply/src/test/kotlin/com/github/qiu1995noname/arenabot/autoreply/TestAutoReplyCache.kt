package com.github.qiu1995noname.arenabot.autoreply

import org.junit.Assert
import org.junit.Test

class TestAutoReplyCache {
    @Test
    fun testReload() {
        Assert.assertFalse(AutoReplyCache.reload("/1/2/3"))
        Assert.assertFalse(AutoReplyCache.reload("build.gradle.kts"))
        Assert.assertFalse(AutoReplyCache.reload("testdata/badcase1"))
        Assert.assertTrue(AutoReplyCache.reload("testdata/badcase1", true))
        Assert.assertFalse(AutoReplyCache.reload("testdata/badcase2"))
        Assert.assertTrue(AutoReplyCache.reload("testdata/badcase2", true))
        Assert.assertTrue(AutoReplyCache.reload("testdata/realdata"))
    }
}
