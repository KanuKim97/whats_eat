package com.example.whats_eat.data.di.module

import com.example.whats_eat.data.common.Constant
import com.google.android.gms.location.CurrentLocationRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* Current Location Request Builder */
@Module
@InstallIn(SingletonComponent::class)
object CurrentLocationModule {
    @Provides
    fun providesDurationMillis(): Long = Constant.DURATION_MILLIS

    @Provides
    fun providesLocationPriority(): Int = Constant.LOCATION_PRIORITY

    @Provides
    @Singleton
    fun providesCurrentLocation(): CurrentLocationRequest =
        CurrentLocationRequest
            .Builder()
            .setDurationMillis(providesDurationMillis())
            .setPriority(providesLocationPriority())
            .build()
}