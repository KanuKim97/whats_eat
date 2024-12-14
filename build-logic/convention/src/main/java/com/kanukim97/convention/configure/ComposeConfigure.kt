package com.example.convention.configure

import com.example.convention.util.androidTestImplementation
import com.example.convention.util.bundle
import com.example.convention.util.debugImplementation
import com.example.convention.util.implementation
import com.example.convention.util.library
import com.example.convention.util.testImplementation
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.composeConfigure() {
    dependencies {
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