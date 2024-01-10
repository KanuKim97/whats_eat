pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "what's_eat"
include(":app")

// Core Module
include(":core:common")
include(":core:data")
include(":core:designsystem")
include(":core:domain")
include(":core:model")
include(":core:network")

// Feature Module
include(":feature:collection")
include(":feature:home")
include(":feature:place-detail")
