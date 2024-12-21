plugins {
    id("com.whats-eat.application")

    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.kanukim97.whats_eat"
    defaultConfig.applicationId = "com.kanukim97.whats_eat"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))

    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:collection"))

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}