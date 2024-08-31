import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.whats-eat.default-library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.domain"
    defaultConfig.buildConfigField("String", "PLACE_API_KEY", getApiKey("MAPS_API_KEY"))
    buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

    testImplementation(libs.junit)
    testImplementation (libs.mockk)
    androidTestImplementation (libs.mockk.android)

    androidTestImplementation(libs.androidx.junit)
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir, providers).getProperty(propertyKey)