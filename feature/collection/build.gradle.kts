plugins {
    id("com.whats-eat.feature")
}

android {
    namespace = "com.example.collection"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
}