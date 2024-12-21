plugins {
    id("com.whats-eat.feature")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.kanukim97.collection"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}