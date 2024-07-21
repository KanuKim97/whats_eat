plugins {
    id("com.whats-eat.feature")
    id(Plugins.maps_secret_gradle_plugin)
}

android {
    namespace = "com.example.detail"
}

dependencies {
    implementation(libs.bundles.gms)

    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}