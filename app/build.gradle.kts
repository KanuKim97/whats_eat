plugins {
    id(Plugins.android_application)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
    id(Plugins.maps_secret_gradle_plugin)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.whats_eat"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.example.whats_eat"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
    }

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

    composeOptions.kotlinCompilerExtensionVersion = "1.5.3"
    buildFeatures.buildConfig = true
    buildFeatures.compose = true
}

dependencies {
    implementation(libs.androidx.core)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    implementation(libs.compose.maps)

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:designsystem"))

    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))
    implementation(project(":feature:collection"))

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.junit4)
    debugImplementation(libs.bundles.compose.debug)
}