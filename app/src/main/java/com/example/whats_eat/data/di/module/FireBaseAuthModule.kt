package com.example.whats_eat.data.di.module

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* Firebase Auth Module */
@Module
@InstallIn(SingletonComponent::class)
object FireBaseAuthModule {
    @Provides
    @Singleton
    fun provideFireAuthInstance(): FirebaseAuth = FirebaseAuth.getInstance()
}