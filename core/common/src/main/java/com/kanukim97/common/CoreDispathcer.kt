package com.kanukim97.common

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IODispatcher


@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher