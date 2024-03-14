object Versions {
    const val core = "1.12.0"
    const val gradle = "8.2.0"
    const val kotlin = "1.9.10"
    const val ksp = "1.9.10-1.0.13"
    const val hilt = "2.49"
    const val coroutine = "1.7.3"
    const val room = "2.6.1"

    const val gms_service = "4.4.0"
    const val maps_secrets = "2.0.1"

    const val play_gms_maps = "18.2.0"
    const val play_gms_location = "21.0.1"

    const val compose = "1.5.3"
    const val compose_BoM = "2023.10.01"
    const val compose_permission = "0.33.2-alpha"
    const val compose_navigation = "2.7.6"
    const val compose_hilt_navigation = "1.1.0"
    const val compose_maps = "2.11.4"
    const val activity_compose = "1.8.2"
    const val compose_lifecycle_runtime = "2.7.0"

    const val junit = "4.13.2"
    const val androidx_junit = "1.1.5"
    const val espresso_core = "3.5.1"

    const val landscapist = "2.2.10"
    const val retrofit = "2.9.0"

    const val mockito_core = "5.8.0"
    const val mockito_kotlin = "5.2.1"
    const val mockk = "1.12.0"

    const val robolectric = "4.11.1"
    const val androidx_test_core = "1.5.0"
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
    const val java_library = "java-library"
    const val ksp = "com.google.devtools.ksp"
    const val hilt = "com.google.dagger.hilt.android"
    const val google_service = "com.google.gms.google-services"
    const val maps_secret_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
}

object Dependencies {
    const val androidx_core = "androidx.core:core:${Versions.core}"
    const val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"

    const val gms_maps = "com.google.android.gms:play-services-maps:${Versions.play_gms_maps}"
    const val gms_location = "com.google.android.gms:play-services-location:${Versions.play_gms_location}"

    const val landscapist_glide = "com.github.skydoves:landscapist-glide:${Versions.landscapist}"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit2_gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room}"

    const val compose_BoM = "androidx.compose:compose-bom:${Versions.compose_BoM}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.activity_compose}"
    const val compose_material3 = "androidx.compose.material3:material3"
    const val compose_runtime_livedata = "androidx.compose.runtime:runtime-livedata"
    const val compose_runtime_lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.compose_lifecycle_runtime}"
    const val compose_material_icons = "androidx.compose.material:material-icons-extended"
    const val compose_navigation = "androidx.navigation:navigation-compose:${Versions.compose_navigation}"
    const val compose_navigation_hilt = "androidx.hilt:hilt-navigation-compose:${Versions.compose_hilt_navigation}"
    const val compose_maps = "com.google.maps.android:maps-compose:${Versions.compose_maps}"
    const val compose_permission = "com.google.accompanist:accompanist-permissions:${Versions.compose_permission}"
    const val compose_ui = "androidx.compose.ui:ui"
    const val compose_ui_graphics = "androidx.compose.ui:ui-graphics"
    const val compose_ui_preview ="androidx.compose.ui:ui-tooling-preview"
    const val compose_ui_junit4 = "androidx.compose.ui:ui-test-junit4"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling"
    const val compose_ui_test_manifest = "androidx.compose.ui:ui-test-manifest"
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junit}"
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val androidx_test_junit_ktx = "androidx.test.ext:junit-ktx:${Versions.androidx_junit}"

    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val androidx_test_core_ktx = "androidx.test:core-ktx:${Versions.androidx_test_core}"

    const val mockito_core = "org.mockito:mockito-core:${Versions.mockito_core}"
    const val mockito_kotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito_kotlin}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockk_android = "io.mockk:mockk-android:${Versions.mockk}"

    const val coroutine_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}"

    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
}

object Module {
    const val app = ":app"

    // core
    const val common = ":core:common"
    const val data = ":core:data"
    const val database = ":core:database"
    const val designsystem = ":core:designsystem"
    const val domain = ":core:domain"
    const val model = ":core:model"
    const val network = ":core:network"
    const val ui = ":core:ui"

    // feature
    const val collection = ":feature:collection"
    const val home = ":feature:home"
    const val detail = ":feature:detail"
}