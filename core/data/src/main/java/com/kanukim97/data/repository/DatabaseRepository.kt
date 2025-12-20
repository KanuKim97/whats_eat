package com.kanukim97.data.repository

import com.kanukim97.model.domain.CollectionModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<CollectionModel>>

    fun readCollectionEntity(placeID: String): Flow<CollectionModel>

    fun saveUserCollection(content: CollectionModel): Flow<Result<Unit>>

    fun deleteUserCollection(content: CollectionModel): Flow<Result<Unit>>
}