plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.designsystem"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.compose = true
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.landscapist.glide)

    // Jetpack Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.junit4)

    testImplementation(libs.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)
}