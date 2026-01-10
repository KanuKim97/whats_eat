import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id("com.github.gmazzo.buildconfig") version "6.0.6"
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
    buildConfigField("String", "PLACE_API_KEY", localProperties.getProperty("MAPS_API_KEY") ?: "")
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}