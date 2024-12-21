package com.kanukim97.data.di

import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.data.repository.DatabaseRepositoryImpl
import com.kanukim97.data.repository.PlaceApiRepository
import com.example.data.repository.PlaceApiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsPlaceAPIRepo(placeAPIRepo: PlaceApiRepositoryImpl): PlaceApiRepository

    @Binds
    fun bindsDataBaseRepo(databaseRepository: DatabaseRepositoryImpl): DatabaseRepository
}