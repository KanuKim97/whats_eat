package com.example.whats_eat.di.module

import com.example.data.api.PlaceAPIService
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.DataBaseRepositoryImpl
import com.example.data.repository.PlaceApiRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DataBaseRepository
import com.example.domain.repository.PlaceApiRepository
import com.example.whats_eat.common.Constant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
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
    fun providesFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)


    @Provides
    @Singleton
    fun providesFireDataBase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideDataBaseRepository(
        auth: FirebaseAuth,
        dataBase: FirebaseDatabase
    ): DataBaseRepository = DataBaseRepositoryImpl(auth, dataBase)

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