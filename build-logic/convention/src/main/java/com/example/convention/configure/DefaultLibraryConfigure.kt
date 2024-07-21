package com.example.convention.configure

import com.android.build.gradle.LibraryExtension
import com.example.convention.constant.Constant
import com.example.convention.util.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.defaultLibraryConfigure(extension: LibraryExtension) {
    extension.apply {
        compileSdk = Constant.COMPILE_SDK

        defaultConfig {
            minSdk = Constant.MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        kotlinExtension.jvmToolchain(17)
    }

    dependencies {
        add("implementation", libs.findLibrary("androidx-core").get())
    }
}