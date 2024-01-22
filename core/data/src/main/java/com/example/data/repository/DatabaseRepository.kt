package com.example.data.repository

import com.example.model.collection.Collection
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<Collection>>

    fun readCollectionEntity(placeID: String): Flow<Collection>

    fun saveUserCollection(content: Collection): Flow<Result<Unit>>

    fun deleteUserCollection(content: Collection): Flow<Result<Unit>>
}