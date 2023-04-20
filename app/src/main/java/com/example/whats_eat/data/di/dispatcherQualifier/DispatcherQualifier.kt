package com.example.whats_eat.data.di.dispatcherQualifier

import javax.inject.Qualifier

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher