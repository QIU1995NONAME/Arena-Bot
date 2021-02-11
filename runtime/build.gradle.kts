plugins {
    `java-library`
    kotlin("jvm")
    distribution
}

dependencies {
    // Kotlin
    runtimeOnly(kotlin("stdlib-jdk8"))
    runtimeOnly(kotlin("stdlib-jdk7"))
    runtimeOnly(kotlin("serialization"))
    runtimeOnly(kotlin("reflect"))
    api(ktor("server-netty"))
    api(ktor("client-cio"))
    api(kotlinx("serialization-json", Versions.Kotlin.serialization))

    // MariaDB
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:+")

    // Jetbrains Annotations
    api("org.jetbrains:annotations:+")

    // Mirai
    runtimeOnly(mirai("core-jvm", Versions.Mirai.core))
    runtimeOnly(mirai("core-utils-jvm", Versions.Mirai.core))
    api(mirai("core-api-jvm", Versions.Mirai.core))
    api(mirai("console", Versions.Mirai.console))
    runtimeOnly(mirai("console-terminal", Versions.Mirai.console))
}

distributions {
    main {
        contents {
            into("runtime") {
                from(project.configurations.runtimeClasspath)
            }
        }
    }
}
