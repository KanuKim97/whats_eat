buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(ProjectClassPath.gms_google_service)
        classpath(ProjectClassPath.maps_secrets_gradle)
    }
}

plugins {
    id(Plugins.android_application) version Versions.gradle apply false
    id(Plugins.android_library) version Versions.gradle apply false
    id(Plugins.kotlin_android) version Versions.kotlin apply false
    id(Plugins.ksp) version Versions.ksp apply false
    id(Plugins.hilt) version Versions.hilt apply false
    id(Plugins.kotlin_jvm) version Versions.kotlin apply false
}