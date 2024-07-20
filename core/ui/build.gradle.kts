plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.ui"
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

    implementation(project(Module.designsystem))
    implementation(project(Module.model))
    implementation(project(Module.domain))

    // Jetpack Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)

    implementation(libs.landscapist.glide)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.junit4)
    debugImplementation(libs.bundles.compose.debug)

    testImplementation(libs.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)
}