package com.kanukim97.network.di

import com.kanukim97.network.PlaceDataSource
import com.kanukim97.network.PlaceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindPlaceDataSource(impl: PlaceDataSourceImpl): PlaceDataSource
}