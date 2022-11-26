package com.hypo.simpledb.kotlin_gradle_plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class SamplePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            tasks.create("exampleTask") {
                doLast {
                    println("OK")
                }
            }
        }
    }
}

apply<SamplePlugin>()