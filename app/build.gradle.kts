import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import java.io.FileInputStream
import java.util.Properties

plugins {
    id(Plugins.android_application)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
    id(Plugins.google_service)
    id(Plugins.maps_secret_gradle_plugin)
}

val apiKey = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
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
        buildConfigField("String", "PLACE_API_KEY", getApiKey("MAPS_API_KEY"))
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    composeOptions.kotlinCompilerExtensionVersion = Versions.compose
    buildFeatures.buildConfig = true
    buildFeatures.compose = true
}

dependencies {
    implementation(Dependencies.androidx_core)

    // Jetpack Compose
    val composeBom = platform(Dependencies.compose_BoM)
    implementation(composeBom)
    implementation(Dependencies.compose_activity)
    implementation(Dependencies.compose_ui)
    implementation(Dependencies.compose_ui_graphics)
    implementation(Dependencies.compose_ui_preview)
    implementation(Dependencies.compose_material3)
    implementation(Dependencies.compose_material_icons)
    implementation(Dependencies.compose_runtime_livedata)
    implementation(Dependencies.compose_navigation)
    implementation(Dependencies.compose_navigation_hilt)
    implementation(Dependencies.compose_maps)

    // Data, Domain Library
    implementation(project(Module.detail))
    implementation(project(Module.data))
    implementation(project(Module.domain))
    implementation(project(Module.model))
    implementation(project(Module.designsystem))

    implementation(project(Module.home))
    implementation(project(Module.detail))
    implementation(project(Module.collection))

    // Kotlin Coroutine
    implementation(Dependencies.coroutine)

    // Dagger-Hilt DI(Dependency Injection) Tool
    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    // Google Android GMS Service Maps SDK, Location
    implementation(Dependencies.gms_maps)
    implementation(Dependencies.gms_location)

    // Jetpack Compose Permission (Accompanist)
    implementation(Dependencies.compose_permission)

    // Retrofit2
    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2_gson_converter)

    // LandScapist-Glide
    implementation(Dependencies.landscapist_glide)

    // Easy Permission
    implementation("com.vmadalin:easypermissions-ktx:1.0.0")

    // Android Test Unit
    testImplementation(Dependencies.junit)
    androidTestImplementation(Dependencies.androidx_junit)
    androidTestImplementation(Dependencies.espresso_core)

    // Compose Test Unit
    androidTestImplementation(composeBom)
    androidTestImplementation(Dependencies.compose_ui_junit4)
    debugImplementation(Dependencies.compose_ui_tooling)
    debugImplementation(Dependencies.compose_ui_test_manifest)
}

fun getApiKey(propertyKey: String): String = gradleLocalProperties(rootDir).getProperty(propertyKey)