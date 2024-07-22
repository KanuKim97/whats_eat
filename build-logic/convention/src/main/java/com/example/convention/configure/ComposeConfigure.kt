package com.example.convention.configure

import com.example.convention.util.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.composeConfigure() {
    dependencies {
        // Jetpack Compose
        val composeBom = platform(libs.findLibrary("compose-bom").get())
        add("implementation", composeBom)
        add("implementation", libs.findBundle("compose").get())
        add("implementation", libs.findBundle("compose-lifecycle").get())

        add("androidTestImplementation", composeBom)
        add("androidTestImplementation", libs.findLibrary("compose-ui-junit4").get())
        add("debugImplementation", libs.findBundle("compose-debug").get())

        // landscapist-glide
        add("implementation", libs.findLibrary("landscapist-glide").get())

        // Android Test
        add("testImplementation", libs.findLibrary("junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-junit").get())
        add("androidTestImplementation", libs.findLibrary("androidx-test-espresso").get())
    }
}