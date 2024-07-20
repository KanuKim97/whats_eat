plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.collection"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
    buildFeatures.compose = true
}

dependencies {
    implementation(libs.androidx.core)

    implementation(project(Module.domain))
    implementation(project(Module.ui))
    implementation(project(Module.designsystem))
    implementation(project(Module.model))

    // Kotlin Coroutine
    implementation(libs.kotlinx.coroutines.android)

    // Dagger-Hilt DI(Dependency Injection) Tool
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    // Jetpack Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.lifecycle)

    implementation(libs.landscapist.glide)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.junit4)
    debugImplementation(libs.bundles.compose.debug)
    
    testImplementation(libs.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)
}