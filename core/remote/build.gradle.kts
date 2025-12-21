import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    id("java-library")
    id("com.github.gmazzo.buildconfig") version "6.0.6"
    alias(libs.plugins.kotlin.jvm)
    kotlin("plugin.serialization") version "2.0.0"
    id("com.google.devtools.ksp")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

buildConfig {
    buildConfigField("String", "MAPS_API_KEY", localProperties.getProperty("MAPS_API_KEY") ?: "")
}


dependencies {
    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.retrofit)
    implementation(libs.retrofit.json.converter)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}