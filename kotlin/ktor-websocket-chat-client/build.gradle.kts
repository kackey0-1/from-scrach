val kotlin_version: String by project  // "2.1.3"
val ktor_version: String by project    // "1.7.10"
val logback_version: String by project // "1.2.11"

plugins {
    application
    // kotlin("jvm") version "1.7.10"
    kotlin("jvm") version "1.6.21"
    id("io.ktor.plugin") version "2.1.3"
}

application {
    mainClass.set("com.hypo.driven.ApplicationKt")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

dependencies {
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-websockets:$ktor_version")
}
