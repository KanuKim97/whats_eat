package com.kanukim97.data.di

import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.data.impl.DatabaseRepositoryImpl
import com.kanukim97.data.repository.PlaceRepository
import com.kanukim97.data.impl.PlaceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsPlaceAPIRepo(placeAPIRepo: PlaceRepositoryImpl): PlaceRepository

    @Binds
    fun bindsDataBaseRepo(databaseRepository: DatabaseRepositoryImpl): DatabaseRepository
}