object Versions {
    const val core = "1.12.0"
    const val gradle = "8.2.0"
    const val kotlin = "1.9.10"
    const val ksp = "1.9.10-1.0.13"
    const val hilt = "2.49"

    const val gms_service = "4.4.0"
    const val maps_secrets = "2.0.1"

    const val compose = "1.5.3"
    const val compose_BoM = "2023.10.01"
    const val activity_compose = "1.8.2"

    const val junit = "4.13.2"
    const val androidx_junit = "1.1.5"
    const val espresso_core = "3.0.2"

    const val landscapist = "2.2.10"
    const val retrofit = "2.9.0"
}

object ProjectClassPath {
    const val gms_google_service = "com.google.gms:google-services:${Versions.gms_service}"
    const val maps_secrets_gradle = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.maps_secrets}"
}

object Plugins {
    const val android_application = "com.android.application"
    const val android_library = "com.android.library"
    const val kotlin_android = "org.jetbrains.kotlin.android"
    const val kotlin_jvm = "org.jetbrains.kotlin.jvm"
    const val ksp = "com.google.devtools.ksp"
    const val hilt = "com.google.dagger.hilt.android"
    const val google_service = "com.google.gms.google-services"
    const val maps_secret_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
}

object Dependencies {
    const val androidx_core = "androidx.core:core:${Versions.core}"

    const val junit = "junit:junit:${Versions.junit}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val espresso_core = "com.android.support.test.espresso:espresso-core:${Versions.espresso_core}"

    const val landscapist_glide = "com.github.skydoves:landscapist-glide:${Versions.landscapist}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit2_gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val compose_BoM = "androidx.compose:compose-bom:${Versions.compose_BoM}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.activity_compose}"
    const val compose_material3 = "androidx.compose.material3:material3"
    const val compose_ui = "androidx.compose.ui:ui"
    const val compose_ui_graphics = "androidx.compose.ui:ui-graphics"
    const val compose_ui_preview ="androidx.compose.ui:ui-tooling-preview"
    const val compose_ui_junit4 = "androidx.compose.ui:ui-test-junit4"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling"
    const val compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest"
}

object Module {
    const val app = ":app"
    const val data = ":data"
    const val domain = ":domain"
}