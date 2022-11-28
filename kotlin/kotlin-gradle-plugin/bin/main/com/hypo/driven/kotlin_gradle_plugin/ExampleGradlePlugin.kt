package com.hypo.driven.kotlin_gradle_plugin


/**
 * Precompiled [ExampleGradle.gradle.kts][com.hypo.driven.kotlin_gradle_plugin.ExampleGradle_gradle] script plugin.
 *
 * @see com.hypo.driven.kotlin_gradle_plugin.ExampleGradle_gradle
 */
class ExampleGradlePlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("com.hypo.driven.kotlin_gradle_plugin.ExampleGradle_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
