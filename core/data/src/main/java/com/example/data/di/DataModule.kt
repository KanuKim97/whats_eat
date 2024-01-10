package com.example.data.di

import com.example.data.repository.PlaceApiRepository
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
}