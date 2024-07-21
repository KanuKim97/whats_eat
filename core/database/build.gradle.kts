plugins {
    id("com.whats-eat.default-library")
    id(Plugins.ksp)
    id(Plugins.hilt)
}

android {
    namespace = "com.example.database"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.core.ktx)
    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}