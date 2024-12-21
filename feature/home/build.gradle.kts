plugins {
    id("com.whats-eat.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kanukim97.home"
}

dependencies {
    implementation(libs.gms.location)
    implementation(libs.compose.permission)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}