package com.example.whats_eat.di.module

import com.example.whats_eat.common.Constant
import com.google.android.gms.location.CurrentLocationRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationRequestModule {
    private fun provideDurationMillis(): Long = Constant.MAPS_INTERVAL_MILLIS

    private fun provideLocationPriority(): Int = Constant.MAPS_PRIORITY_HIGH

    @Provides
    @Singleton
    fun provideLocationRequest(): CurrentLocationRequest =
        CurrentLocationRequest
            .Builder()
            .setDurationMillis(provideDurationMillis())
            .setPriority(provideLocationPriority())
            .build()
}