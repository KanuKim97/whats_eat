plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.collection"
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
    androidTestImplementation(Dependencies.espresso_core)
}