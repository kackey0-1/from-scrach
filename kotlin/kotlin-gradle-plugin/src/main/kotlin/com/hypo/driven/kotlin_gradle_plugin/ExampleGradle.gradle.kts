package com.hypo.driven.kotlin_gradle_plugin

import org.gradle.api.Project

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