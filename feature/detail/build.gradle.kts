plugins {
    id("com.whats-eat.feature")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.kanukim97.detail"
}

dependencies {
    implementation(libs.bundles.gms)

    implementation(project(":core:util"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
}