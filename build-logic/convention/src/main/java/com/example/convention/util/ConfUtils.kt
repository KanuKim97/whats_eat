package com.example.convention.util

import org.gradle.api.artifacts.Dependency
import org.gradle.kotlin.dsl.support.delegates.DependencyHandlerDelegate

internal fun DependencyHandlerDelegate.implementation(library: Any): Dependency? {
    return add("implementation", library)
}

internal fun DependencyHandlerDelegate.debugImplementation(library: Any): Dependency? {
    return add("debugImplementation", library)
}

internal fun DependencyHandlerDelegate.testImplementation(library: Any): Dependency? {
    return add("testImplementation", library)
}

internal fun DependencyHandlerDelegate.androidTestImplementation(library: Any): Dependency? {
    return add("androidTestImplementation", library)
}

internal fun DependencyHandlerDelegate.ksp(library: Any): Dependency? {
    return add("ksp", library)
}
