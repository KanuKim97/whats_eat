package com.example.whats_eat.data.di.module

import android.content.Context
import android.location.Geocoder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationContext::class)
object GeoCoderModule {
    @Provides
    @Singleton
    fun provideGeocoder(context: Context): Geocoder = Geocoder(context, Locale.KOREA)
}