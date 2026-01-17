plugins {
    id("com.whats-eat.feature")
}

android {
    namespace = "com.kanukim97.home"
}

dependencies {
    implementation(libs.bundles.gms)
    implementation(libs.compose.permission)

    implementation(project(":core:util"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
}