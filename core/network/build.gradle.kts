import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.whats-eat.default-library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

val apiKey = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    namespace = "com.example.network"

    defaultConfig.buildConfigField("String", "PLACE_API_KEY", getApiKey("MAPS_API_KEY"))
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(project(":core:model"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)