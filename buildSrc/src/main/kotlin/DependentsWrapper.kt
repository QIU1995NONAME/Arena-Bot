fun ktor(id: String, version: String = Versions.Kotlin.ktor) =
    "io.ktor:ktor-$id:$version"

fun kotlinx(id: String, version: String = "+") =
    "org.jetbrains.kotlinx:kotlinx-$id:$version"

fun mirai(id: String, version: String = "+") =
    "net.mamoe:mirai-$id:$version"

