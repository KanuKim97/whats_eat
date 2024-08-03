package com.example.network.di

import com.example.network.PlaceDataSource
import com.example.network.PlaceDataSourceImpl
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