object Versions {
    const val compose = "1.5.3"

    const val androidx_junit = "1.1.5"
    const val espresso_core = "3.5.1"
}

object Plugins {
    const val android_application = "com.android.application"
    const val android_library = "com.android.library"
    const val kotlin_android = "org.jetbrains.kotlin.android"
    const val kotlin_jvm = "org.jetbrains.kotlin.jvm"
    const val java_library = "java-library"
    const val ksp = "com.google.devtools.ksp"
    const val hilt = "com.google.dagger.hilt.android"
    const val maps_secret_gradle_plugin = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
}

object TestDependencies {
    const val androidx_junit = "androidx.test.ext:junit:${Versions.androidx_junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
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