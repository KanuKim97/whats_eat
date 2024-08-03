package com.example.network.di

import com.example.network.util.addDefaultTimeOut
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
        .build()
}