package com.example.whats_eat.data.di.module

import com.example.data.repository.AuthRepositoryImpl
import com.example.data.repository.DataBaseRepositoryImpl
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.DataBaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun providesFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun providesFireDataBase(): FirebaseDatabase = FirebaseDatabase.getInstance()

    @Singleton
    @Provides
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(auth)

    @Singleton
    @Provides
    fun provideDataBaseRepository(
        auth: FirebaseAuth,
        dataBase: FirebaseDatabase
    ): DataBaseRepository = DataBaseRepositoryImpl(auth, dataBase)
}