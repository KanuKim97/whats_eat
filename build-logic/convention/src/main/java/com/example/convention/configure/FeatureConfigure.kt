package com.example.convention.configure

import com.android.build.gradle.LibraryExtension
import com.example.convention.constant.Constant
import com.example.convention.util.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.featureConfigure(extension: LibraryExtension) {
    extension.apply {
        compileSdk = Constant.COMPILE_SDK

        defaultConfig {
            minSdk = Constant.MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        kotlinExtension.jvmToolchain(17)

        buildFeatures.buildConfig = true
        buildFeatures.compose = true
    }

    dependencies {
        add("implementation", libs.findLibrary("androidx-core").get())

        // Kotlin Coroutines
        add("implementation", libs.findLibrary("kotlinx-coroutines-android").get())

        // Dagger Hilt
        add("implementation", libs.findLibrary("hilt").get())
        add("ksp", libs.findLibrary("hilt-compiler").get())

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