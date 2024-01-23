package com.example.data.repository

import com.example.model.feature.CollectionModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<CollectionModel>>

    fun readCollectionEntity(placeID: String): Flow<CollectionModel>

    fun saveUserCollection(content: CollectionModel): Flow<Result<Unit>>

    fun deleteUserCollection(content: CollectionModel): Flow<Result<Unit>>
}