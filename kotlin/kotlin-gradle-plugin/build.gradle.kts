plugins {
    // 現状ではGradle Kotlin DSLのKotlinは1.5.21に依存していると警告が出るので、1.5.21にしている。
    id("org.jetbrains.kotlin.jvm") version "1.5.21"
    // Kotlinで開発する上で便利なDSLを提供してくれる
    `kotlin-dsl`
    // Gradleのプラグインを作成するのに必要
    `java-gradle-plugin`
    // Mavenに配布するのに使用する
    `maven-publish`
}

gradlePlugin {
    (plugins) {
        // package名が net.matsudamper.kotlin_gradle_plugin_example
        // ファイル名が ExampleGradle.gradle.kts というファイルという場合の例
        "com.hypo.driven.kotlin_gradle_plugin_example.ExampleGradle" {
            // 以下のように参照できる
            // id("kotlin-gradle-plugin-example") version "1.0"
            id = "kotlin-gradle-plugin-example"
            version = "1.0"
            // "${package名}.${ファイル名の先頭}Plugin" を付ける
            // (<name>.gradle.kts -> <name>Plugin)
            implementationClass = "com.hypo.driven.kotlin_gradle_plugin_example.ExampleGradlePlugin"
        }
    }
}
