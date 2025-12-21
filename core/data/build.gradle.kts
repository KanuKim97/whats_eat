import java.util.Properties

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}

plugins {
    id("com.whats-eat.default-library")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}


android {
    namespace = "com.kanukim97.data"

    buildTypes {
        release {
            buildConfigField("String", "API_KEY", localProperties.getProperty("MAPS_API_KEY"))
        }

        debug {
            buildConfigField("String", "API_KEY", localProperties.getProperty("MAPS_API_KEY"))
        }
    }

    buildFeatures.buildConfig = true
}

dependencies {
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    implementation(project(":core:util"))
    implementation(project(":core:database"))
    implementation(project(":core:remote"))

    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.androidx.junit)
}