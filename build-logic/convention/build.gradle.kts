import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}


dependencies {
    compileOnly("com.android.tools.build:gradle:8.2.0")
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("feature") {
            id = "com.whats-eat.feature"
            implementationClass = "FeaturePlugin"
        }
        register("compose") {
            id = "com.whats-eat.compose"
            implementationClass = "ComposePlugin"
        }
        register("default-library") {
            id = "com.whats-eat.default-library"
            implementationClass = "DefaultLibraryPlugin"
        }
    }
}