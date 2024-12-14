package com.example.convention.configure

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.example.convention.constant.Constant
import com.example.convention.util.implementation
import com.example.convention.util.library
import com.example.convention.util.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.applicationConfigure(extension: BaseAppModuleExtension) {
    extension.apply {
        kotlinExtension.jvmToolchain(17)

        compileSdk = Constant.COMPILE_SDK

        defaultConfig {
            minSdk = Constant.MIN_SDK
            targetSdk = Constant.TARGET_SDK
            versionCode = Constant.VERSION_CODE
            versionName = Constant.VERSION_NAME
        }

        buildFeatures.apply {
            buildConfig = true
            compose = true
        }
    }

    dependencies { implementation(library("androidx-core")) }
}