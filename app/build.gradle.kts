plugins {
    id("com.whats-eat.application")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.whats_eat"

    defaultConfig.applicationId = "com.example.whats_eat"

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