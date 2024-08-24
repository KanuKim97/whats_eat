pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    includeBuild("build-logic")
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
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:database")

// Feature Module
include(":feature:collection")
include(":feature:home")
include(":feature:detail")
