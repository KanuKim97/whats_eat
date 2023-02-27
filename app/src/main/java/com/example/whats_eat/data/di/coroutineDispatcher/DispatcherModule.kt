package com.example.whats_eat.data.di.coroutineDispatcher

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @IoDispatcher
    @Provides
    fun providesIoDispatcher() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher() = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher() = Dispatchers.Default
}