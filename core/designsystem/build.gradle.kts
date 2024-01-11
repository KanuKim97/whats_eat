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
    implementation(Dependencies.androidx_core)

    // Jetpack Compose
    val composeBom = platform(Dependencies.compose_BoM)
    implementation(composeBom)
    implementation(Dependencies.compose_activity)
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_graphics)
    implementation(Dependencies.compose_ui_preview)
    implementation(Dependencies.compose_material3)
    implementation(Dependencies.compose_material_icons)
    implementation(Dependencies.compose_runtime_livedata)
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.compose_navigation_hilt)

    implementation(Dependencies.landscapist_glide)

    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.compose_ui_junit4)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx_junit)
    androidTestImplementation(Dependencies.espresso_core)
}