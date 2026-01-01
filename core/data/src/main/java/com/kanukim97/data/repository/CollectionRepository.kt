package com.kanukim97.data.repository

import com.kanukim97.data.model.PlaceCollection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun readAllCollections(): Flow<List<PlaceCollection>>

    fun readCollection(placeID: String): Flow<PlaceCollection>

    suspend fun saveCollection(id: String, name: String, latLng: String, imageUrl: String)

    suspend fun deleteCollection(id: String)
}