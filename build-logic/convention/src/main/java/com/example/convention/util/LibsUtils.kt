package com.example.convention.util

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.VersionConstraint
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency

internal val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun Project.version(alias: String): VersionConstraint {
    return libs.findVersion(alias).get()
}

internal fun Project.plugin(alias: String): Provider<PluginDependency> {
    return libs.findPlugin(alias).get()
}

internal fun Project.library(alias: String): Provider<MinimalExternalModuleDependency> {
    return libs.findLibrary(alias).get()
}

internal fun Project.bundle(alias: String): Provider<ExternalModuleDependencyBundle> {
    return libs.findBundle(alias).get()
}
