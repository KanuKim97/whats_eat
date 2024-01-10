package com.example.network.di

import com.example.network.constant.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .callTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()
}