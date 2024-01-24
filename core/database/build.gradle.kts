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

    testImplementation(Dependencies.junit)
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("androidx.test:core-ktx:1.5.0")
    testImplementation("org.robolectric:robolectric:4.11.1")
    testImplementation(Dependencies.coroutine_test)
    androidTestImplementation(Dependencies.androidx_junit)
}