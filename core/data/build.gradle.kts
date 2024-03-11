plugins {
    id(Plugins.android_library)
    id(Plugins.kotlin_android)
    id(Plugins.ksp)
    id(Plugins.hilt)
}

kotlin.jvmToolchain(AppConfig.jdkVersion)

android {
    namespace = "com.example.data"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(Dependencies.androidx_core)

    implementation(project(Module.common))
    implementation(project(Module.database))
    implementation(project(Module.network))
    implementation(project(Module.model))

    testImplementation(TestDependencies.mockito_core)
    testImplementation(TestDependencies.mockito_kotlin)
    testImplementation(TestDependencies.coroutine_test)
    testImplementation(TestDependencies.junit)
    androidTestImplementation(TestDependencies.androidx_junit)

    implementation(Dependencies.hilt)
    ksp(Dependencies.hilt_compiler)

    implementation(Dependencies.retrofit2)
    implementation(Dependencies.retrofit2_gson_converter)
}