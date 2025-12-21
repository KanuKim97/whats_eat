package com.kanukim97.data.repository

import com.kanukim97.data.model.PlaceCollection
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollections(): Flow<List<PlaceCollection>>

    fun readCollection(placeID: String): Flow<PlaceCollection>


    suspend fun deleteCollection(id: String)
}