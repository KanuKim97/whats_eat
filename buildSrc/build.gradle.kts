plugins { `kotlin-dsl` }
repositories.mavenCentral()

kotlin.jvmToolchain(17)
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17