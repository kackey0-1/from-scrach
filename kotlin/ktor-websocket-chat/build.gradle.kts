val kotlin_version: String by project  // "2.1.3"
val ktor_version: String by project    // "1.7.10"
val logback_version: String by project // "1.2.11"

plugins {
    application
    // kotlin("jvm") version "1.7.10"
    kotlin("jvm") version "1.6.21"
    id("io.ktor.plugin") version "2.1.3"
    // id("org.jetbrains.kotlin.plugin.serialization") version "1.7.10"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21"
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-websockets:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
