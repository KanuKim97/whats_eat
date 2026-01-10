plugins {
    id("com.whats-eat.feature")
}

android {
    namespace = "com.kanukim97.collection"
}

dependencies {
    implementation(libs.kotlinx.immutable.collections)

    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
}