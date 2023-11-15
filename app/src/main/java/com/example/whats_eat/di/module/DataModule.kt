package com.example.whats_eat.di.module

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