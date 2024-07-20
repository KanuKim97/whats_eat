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

    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
    buildFeatures.buildConfig = true
    buildFeatures.compose = true
}

dependencies {
    implementation(libs.androidx.core)

    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
    implementation(libs.compose.maps)

    implementation(project(Module.detail))
    implementation(project(Module.data))
    implementation(project(Module.domain))
    implementation(project(Module.model))
    implementation(project(Module.designsystem))

    implementation(project(Module.home))
    implementation(project(Module.detail))
    implementation(project(Module.collection))

    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)

    androidTestImplementation(composeBom)
    androidTestImplementation(libs.compose.ui.junit4)
    debugImplementation(libs.bundles.compose.debug)
}