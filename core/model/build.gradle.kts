plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

kotlin.jvmToolchain(17)
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17