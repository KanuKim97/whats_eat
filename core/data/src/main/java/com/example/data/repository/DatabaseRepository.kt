package com.example.data.repository

import com.example.database.model.UserCollectionEntity
import com.example.model.collection.Collection
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<UserCollectionEntity>>

    fun readCollectionEntity(placeID: String): Flow<UserCollectionEntity>

    fun saveUserCollection(content: Collection): Flow<Result<Unit>>

    fun deleteUserCollection(content: Collection): Flow<Result<Unit>>
}