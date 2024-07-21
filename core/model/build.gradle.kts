plugins {
    id(Plugins.java_library)
    id(Plugins.kotlin_jvm)
}

kotlin.jvmToolchain(17)
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17