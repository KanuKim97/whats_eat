plugins {
    id("com.whats-eat.feature")
}

android {
    namespace = "com.example.home"
}

dependencies {
    implementation(libs.gms.location)
    implementation(libs.compose.permission)

    implementation(project(":core:common"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}