plugins {
    id("com.whats-eat.default-library")
    id(Plugins.ksp)
    id(Plugins.hilt)
}

android {
    namespace = "com.example.common"
}

dependencies {
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}