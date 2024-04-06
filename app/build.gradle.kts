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
    implementation(Dependencies.androidx_core)

    val composeBom = platform(Dependencies.compose_BoM)
    implementation(composeBom)
    implementation(Dependencies.compose_activity)
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_graphics)
    implementation(Dependencies.compose_ui_preview)
    implementation(Dependencies.compose_material3)
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.compose_navigation_hilt)
    implementation(Dependencies.compose_maps)

    implementation(project(Module.detail))
    implementation(project(Module.data))
    implementation(project(Module.domain))
    implementation(project(Module.model))
    implementation(project(Module.designsystem))

    implementation(project(Module.home))
    implementation(project(Module.detail))
    implementation(project(Module.collection))

    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidx_junit)
    androidTestImplementation(TestDependencies.espresso_core)

    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.compose_ui_junit4)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)
}