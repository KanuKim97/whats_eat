plugins {
    id("com.whats-eat.feature")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kanukim97.detail"
}

dependencies {
    implementation(libs.bundles.gms)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}