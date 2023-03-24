package com.example.whats_eat.data.di.coroutineDispatcher

import javax.inject.Qualifier

/* Coroutine Dispatcher Annotation Class */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher