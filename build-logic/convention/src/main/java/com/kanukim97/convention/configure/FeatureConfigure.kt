package com.example.convention.configure

import com.android.build.gradle.LibraryExtension
import com.example.convention.constant.Constant
import com.example.convention.util.androidTestImplementation
import com.example.convention.util.bundle
import com.example.convention.util.debugImplementation
import com.example.convention.util.implementation
import com.example.convention.util.ksp
import com.example.convention.util.library
import com.example.convention.util.libs
import com.example.convention.util.testImplementation
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
        implementation(library("androidx-core"))

        // Kotlin Coroutines
        implementation(library("kotlinx-coroutines-android"))

        // Dagger Hilt
        implementation(library("hilt"))
        ksp(library("hilt-compiler"))

        // Jetpack Compose
        val composeBom = platform(library("compose-bom"))
        implementation(composeBom)
        implementation(bundle("compose"))
        implementation(bundle("compose-lifecycle"))

        androidTestImplementation(composeBom)
        androidTestImplementation(library("compose-ui-junit4"))
        debugImplementation(bundle("compose-debug"))

        // landscapist-glide
        implementation(library("landscapist-glide"))

        // Android Test
        testImplementation(library("junit"))
        androidTestImplementation(library("androidx-junit"))
        androidTestImplementation(library("androidx-test-espresso"))
    }
}