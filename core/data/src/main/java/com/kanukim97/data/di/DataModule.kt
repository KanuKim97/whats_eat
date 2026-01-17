package com.kanukim97.data.di

import com.kanukim97.data.impl.CollectionRepositoryImpl
import com.kanukim97.data.impl.PlaceRepositoryImpl
import com.kanukim97.domain.repository.CollectionRepository
import com.kanukim97.domain.repository.RestaurantRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsPlaceAPIRepo(placeAPIRepo: PlaceRepositoryImpl): RestaurantRepository

    @Binds
    fun bindsDataBaseRepo(databaseRepository: CollectionRepositoryImpl): CollectionRepository
}