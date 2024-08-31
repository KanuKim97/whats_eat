import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.whats-eat.default-library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.network"

    defaultConfig.buildConfigField("String", "PLACE_API_KEY", getApiKey("MAPS_API_KEY"))
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging.interceptor)

    implementation(libs.retrofit)
    implementation(libs.retrofit.json.converter)
    implementation(libs.kotlinx.serialization.json)


    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir, providers).getProperty(propertyKey)