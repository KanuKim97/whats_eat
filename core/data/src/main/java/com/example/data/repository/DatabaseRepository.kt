package com.example.data.repository

import com.example.database.model.UserCollectionEntity
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<UserCollectionEntity>>

    fun readCollectionEntity(placeID: String): Flow<UserCollectionEntity>

    fun saveUserCollection(content: UserCollectionEntity): Flow<Result<Unit>>

    fun deleteUserCollection(content: UserCollectionEntity): Flow<Result<Unit>>
}