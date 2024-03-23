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
    implementation(Dependencies.androidx_core)

    implementation(Dependencies.room)
    ksp(Dependencies.room_compiler)
    implementation(Dependencies.room_ktx)

    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    testImplementation(TestDependencies.junit)
    testImplementation(TestDependencies.androidx_test_core_ktx)
    testImplementation(TestDependencies.androidx_test_junit_ktx)
    testImplementation(TestDependencies.robolectric)
    testImplementation(TestDependencies.coroutine_test)
    androidTestImplementation(TestDependencies.androidx_junit)
}