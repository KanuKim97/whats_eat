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
    namespace = "com.example.domain"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "PLACE_API_KEY", getApiKey("MAPS_API_KEY"))
    }
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(Dependencies.androidx_core)

    implementation(project(Module.common))
    implementation(project(Module.data))
    implementation(project(Module.model))

    // Dagger-Hilt DI(Dependency Injection) Tool
    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    testImplementation(TestDependencies.junit)
    testImplementation (TestDependencies.mockk)
    androidTestImplementation (TestDependencies.mockk_android)

    androidTestImplementation(TestDependencies.androidx_junit)
}

fun getApiKey(
    propertyKey: String
): String = gradleLocalProperties(rootDir).getProperty(propertyKey)