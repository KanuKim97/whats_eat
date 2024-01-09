package com.example.whats_eat.di.module

import com.example.data.api.PlaceAPIService
import com.example.data.repository.PlaceApiRepositoryImpl
import com.example.domain.repository.PlaceApiRepository
import com.example.whats_eat.common.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

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
            .baseUrl(Constant.PLACE_API_URI)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): PlaceAPIService =
        retrofit.create(PlaceAPIService::class.java)

    @Provides
    @Singleton
    fun provideApiBaseRepository(apiService: PlaceAPIService): PlaceApiRepository =
        PlaceApiRepositoryImpl(apiService)

}