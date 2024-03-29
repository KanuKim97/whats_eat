plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
    id(Plugins.maps_secret_gradle_plugin)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.detail"
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
    implementation(project(Module.ui))
    implementation(project(Module.designsystem))
    implementation(project(Module.model))

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
    implementation(Dependencies.compose_runtime_lifecycle)
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.compose_navigation_hilt)

    implementation(Dependencies.landscapist_glide)

    implementation(Dependencies.gms_location)
    implementation(Dependencies.gms_maps)
    implementation(Dependencies.compose_maps)

    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.compose_ui_junit4)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)
}