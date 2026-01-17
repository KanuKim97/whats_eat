package com.kanukim97.domain.repository

import com.kanukim97.domain.entities.RestaurantCollectionItem
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun readAllCollections(): Flow<List<RestaurantCollectionItem>>

    fun readCollection(placeID: String): Flow<RestaurantCollectionItem>

    suspend fun saveCollection(id: String, name: String, latLng: String, imageUrl: String)

    suspend fun deleteCollection(id: String)
}