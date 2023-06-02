package com.example.whats_eat.data.di.module

import com.example.data.api.PlaceAPIService
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.DataBaseRepositoryImpl
import com.example.data.repository.PlaceApiRepositoryBaseImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DataBaseRepository
import com.example.domain.repository.PlaceApiRepositoryBase
import com.example.whats_eat.data.common.Constant
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

}