plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.domain"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(Dependencies.androidx_core)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx_junit)

    // Dagger-Hilt DI(Dependency Injection) Tool
    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)
}