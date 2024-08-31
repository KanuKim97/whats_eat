plugins {
    id("com.whats-eat.default-library")
    id("com.whats-eat.compose")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))
}