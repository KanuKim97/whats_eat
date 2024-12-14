package com.example.network.di

import com.example.network.api.PlaceApiService
import com.example.network.constant.Constants
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    fun provideNetworkJson() = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    fun provideRetrofitInstance(
        networkJson: Json,
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl(Constants.PLACE_API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType())
        )
        .build()

    @Provides
    fun providePlaceApiService(
        retrofit: Retrofit
    ): PlaceApiService = retrofit.create<PlaceApiService>()
}