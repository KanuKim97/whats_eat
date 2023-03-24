package com.example.whats_eat.data.di.module

import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.repository.PlaceApiRepository
import com.example.whats_eat.data.remote.IGoogleAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/* Google Place API Service Module */
@Module
@InstallIn(SingletonComponent::class)
object PlaceApiModule {
    @Provides
    fun providesBaseURL(): String = Constant.PLACE_API_URL

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesApiClient(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(providesBaseURL())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): IGoogleAPIService =
        retrofit.create(IGoogleAPIService::class.java)

    @Provides
    @Singleton
    fun providesApiRepository(googleAPIService: IGoogleAPIService): PlaceApiRepository =
        PlaceApiRepository(googleAPIService)
}