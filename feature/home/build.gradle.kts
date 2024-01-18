plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
    id(Plugins.google_service)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.home"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
    buildFeatures.compose = true
}

dependencies {
    implementation(Dependencies.androidx_core)

    implementation(project(Module.common))
    implementation(project(Module.domain))
    implementation(project(Module.designsystem))
    implementation(project(Module.model))
    implementation(project(Module.ui))

    implementation(Dependencies.gms_location)

    // Kotlin Coroutine
    implementation(Dependencies.coroutine)

    // Dagger-Hilt DI(Dependency Injection) Tool
    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

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
    implementation(Dependencies.compose_permission)

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.compose_ui_junit4)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)

    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx_junit)
    androidTestImplementation(Dependencies.espresso_core)
}