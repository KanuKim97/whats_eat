import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

val apiKey = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.data"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(Dependencies.androidx_core)

    implementation(project(Module.common))
    implementation(project(Module.model))
    implementation(project(Module.network))

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx_junit)


    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2_gson_converter)
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)