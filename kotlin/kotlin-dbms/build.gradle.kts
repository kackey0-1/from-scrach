import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    application
}

group = "com.hypo.driven.kotlin-dbms"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // derby
    implementation("org.apache.derby:derby:10.16.1.1")
    implementation("org.apache.derby:derbytools:10.16.1.1")
    implementation("org.apache.derby:derbyshared:10.16.1.1")

    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("com.google.guava:guava:31.1-jre")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

application {
    mainClass.set("com.hypo.driven.simpledb.server.ServerKt")
}

tasks.test {
    useJUnitPlatform()
}
