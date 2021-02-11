package com.github.qiu1995noname.arenabot.entity

import java.util.*
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.collections.HashSet
import kotlin.concurrent.read
import kotlin.concurrent.write

object Cytus2Actors {
    private val rwLock = ReentrantReadWriteLock()

    // MAP( name -> SET(nick) )
    private val mapActorNicks = TreeMap<String, HashSet<String>>(String.CASE_INSENSITIVE_ORDER)

    // MAP( nick -> name )
    private val mapActorNickIndex = TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER)

    fun loadActors(actors: Map<String, List<String>>) = rwLock.write {
        mapActorNicks.clear()
        mapActorNickIndex.clear()
        actors.forEach { (name, nicks) ->
            if (!mapActorNicks.containsKey(name)) {
                mapActorNicks[name] = HashSet()
            }
            nicks.forEach { nick ->
                mapActorNicks[name]!!.add(nick)
                mapActorNickIndex[nick] = name
            }
        }
    }

    fun getActorName(nick: String): String? = rwLock.read {
        if (mapActorNickIndex.containsKey(nick)) {
            return mapActorNickIndex[nick]
        }
        if (mapActorNicks.containsKey(nick)) {
            return mapActorNicks.floorKey(nick)
        }
        return null
    }
}
