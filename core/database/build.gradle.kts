plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.database"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.androidx.core)

    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.androidx_test_core_ktx)
    testImplementation(TestDependencies.androidx_test_junit_ktx)
    testImplementation(TestDependencies.robolectric)
    testImplementation(TestDependencies.coroutine_test)
    androidTestImplementation(TestDependencies.androidx_junit)
}