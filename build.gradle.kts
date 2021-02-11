buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin")
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }
}

allprojects {
    repositories {
        jcenter()
        maven {
            url = uri("https://repo.spongepowered.org/maven")
        }
    }
}

plugins {
    `java-library`
    kotlin("jvm") version Versions.Kotlin.lang apply false
    kotlin("kapt") version Versions.Kotlin.lang apply false
    kotlin("plugin.serialization") version Versions.Kotlin.lang apply false
    id("org.spongepowered.plugin") version Versions.SpongePowered.plugin apply false
}
