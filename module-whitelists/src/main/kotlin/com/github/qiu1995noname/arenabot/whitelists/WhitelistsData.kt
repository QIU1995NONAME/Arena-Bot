package com.github.qiu1995noname.arenabot.whitelists

import kotlinx.serialization.Serializable
import net.mamoe.mirai.Bot
import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.value

object WhitelistsData : AutoSavePluginData(
        WhitelistsData::class.simpleName!!
) {
    private const val MS_PER_DAY = 1000L * 60 * 60 * 24

    enum class Feature {
        ARENA,
        SONG_SELECT,
        CYC,
    }

    /**
     * 用来统计各个模块的使用情况
     */
    @Serializable
    data class Statistics(
            // 功能名 -> Map(群号 -> Set(使用时刻))
            val counts: HashMap<String, HashMap<Long, HashSet<Long>>> = HashMap(),
    )

    private val statistics by value<Statistics>()

    private fun removeOldData() = synchronized(this) {
        val now = System.currentTimeMillis() / MS_PER_DAY * MS_PER_DAY
        statistics.counts.forEach { (_, map) ->
            map.forEach { (_, set) ->
                set.removeIf { it < now - MS_PER_DAY * 30 }
            }
        }
    }

    fun summary(bot: Bot?, dayCount: Int = 7): String = synchronized(this) {
        removeOldData()
        val days = if (dayCount < 3) 3 else dayCount
        val builder = StringBuilder()
        val now = System.currentTimeMillis()
        // 筛选所有已加入，不在白名单的群
        if (bot != null) {
            builder.append("不在白名单的群:\n")
            bot.groups.filter { it.id !in WhitelistsConfig.grantedSets.allowedGroups }.forEach {
                builder.append("${it.id} (${it.name})\n")
            }
            builder.append("=================\n")
        }
        // 统计各模块使用频率
        builder.append("$days 天内使用情况:\n")
        statistics.counts.forEach { (featureName, map) ->
            builder.append("=================\n")
            builder.append("$featureName:\n")

            // Group -> Rate
            val resMap = HashMap<Long, Int>()

            // 筛选所有白名单的群
            map.filter { entry ->
                entry.key in WhitelistsConfig.grantedSets.allowedGroups
            }.forEach { (groupId, samples) ->
                val res = samples.filter { it > now - MS_PER_DAY * days }.size
                if (res != 0) {
                    resMap[groupId] = res
                }
            }
            // 找到所有有记录的群
            resMap.forEach { (groupId, rate) ->
                builder.append("  $rate  $groupId")
                if (bot != null) {
                    val groupName = bot.getGroup(groupId)?.name ?: "不在该群"
                    builder.append(" (${groupName})")
                }
                builder.append("\n")
            }
            //            // 找到所有白名单内无记录的群
            //            WhitelistsConfig.grantedSets.allowedGroups.filter { it !in resMap }.forEach {
            //                builder.append("  0  $it")
            //                if (bot != null) {
            //                    val groupName = bot.getGroup(it)?.name ?: "不在该群"
            //                    builder.append(" (${groupName})")
            //                }
            //                builder.append("\n")
            //            }
        }
        return builder.toString()
    }

    /**
     * 采样使用频率
     *
     * @param feature 功能
     * @param groupId 群号
     */
    fun sampling(feature: Feature, groupId: Long): Unit = synchronized(this) {
        removeOldData()
        if (feature.name !in statistics.counts.keys) {
            statistics.counts[feature.name] = HashMap()
        }
        if (groupId !in statistics.counts[feature.name]!!.keys) {
            statistics.counts[feature.name]!![groupId] = HashSet()
        }
        statistics.counts[feature.name]!![groupId]!!.add(System.currentTimeMillis())
    }
}
