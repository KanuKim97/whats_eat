plugins {
    id("com.whats-eat.default-library")
    id("com.whats-eat.compose")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.designsystem"
}