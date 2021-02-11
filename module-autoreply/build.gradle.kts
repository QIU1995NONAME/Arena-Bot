plugins {
    `java-library`
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.serialization")
}

version = Versions.project

dependencies {
    // runtime
    implementation(project(":runtime"))
    implementation(project(":module-whitelists"))
    // Google Auto Service
    kapt("com.google.auto.service:auto-service:1.+")
    compileOnly("com.google.auto.service:auto-service-annotations:1.+")
    // JUnit
    testImplementation("junit:junit:4.+")
}

kotlin.target.compilations.all {
    kotlinOptions.freeCompilerArgs += "-Xjvm-default=enable"
    kotlinOptions.jvmTarget = "1.8"
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Implementation-Version"] = project.version
    }
}
