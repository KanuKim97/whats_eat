package com.example.convention.configure

import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.constant.Constant
import com.example.convention.util.libs
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

internal fun Project.applicationConfigure(extension: ApplicationExtension) {
    extension.apply {
        compileSdk = Constant.COMPILE_SDK

        defaultConfig {
            minSdk = Constant.MIN_SDK
            targetSdk = Constant.TARGET_SDK
            versionCode = Constant.VERSION_CODE
            versionName = Constant.VERSION_NAME
        }

        kotlinExtension.jvmToolchain(17)

        composeOptions.kotlinCompilerExtensionVersion = libs.findVersion("compose").get().requiredVersion
        buildFeatures.apply {
            buildConfig = true
            compose = true
        }
    }
}