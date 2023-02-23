package com.example.whats_eat.data.di.module

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* FireBase RealTimeDataBase Module */
@Module
@InstallIn(SingletonComponent::class)
object FireBaseDBModule {
    @Provides
    @Singleton
    fun provideFireBaseRTDBInstance(): FirebaseDatabase = FirebaseDatabase.getInstance()
}