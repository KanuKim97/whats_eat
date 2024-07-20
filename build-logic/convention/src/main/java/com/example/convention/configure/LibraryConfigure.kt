package com.example.convention.configure

import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.constant.Constant
import org.gradle.api.Project

internal fun Project.libraryConfigure(extension: ApplicationExtension) {
    extension.apply {
        compileSdk = Constant.COMPILE_SDK

        defaultConfig {
            minSdk = Constant.MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
}