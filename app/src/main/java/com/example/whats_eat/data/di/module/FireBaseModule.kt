package com.example.whats_eat.data.di.module

import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/* Firebase Auth, RealTimeDataBase Module */
@Module
@InstallIn(SingletonComponent::class)
object FireBaseModule {
    private val fireAuth = FirebaseAuth.getInstance()
    private val fireRealDB = FirebaseDatabase.getInstance()
    private val fireRealDBRef = fireRealDB.reference.child(fireAuth.uid.toString())

    @Provides
    fun provideFireAuthInstance() = fireAuth

    @Provides
    fun provideFireRealDBReference() = fireRealDBRef

    @Provides
    @Singleton
    fun providesFireBaseRepository() =
        FirebaseRepository(provideFireAuthInstance(),  provideFireRealDBReference())
}