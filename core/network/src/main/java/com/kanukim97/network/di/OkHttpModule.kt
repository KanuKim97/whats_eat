package com.kanukim97.network.di

import com.kanukim97.network.util.addDefaultTimeOut
import com.kanukim97.network.util.httpLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()
        .newBuilder()
        .addDefaultTimeOut()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}