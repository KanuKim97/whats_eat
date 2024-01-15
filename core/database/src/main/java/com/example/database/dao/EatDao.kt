package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.database.model.UserCollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EatDao {
    @Query("SELECT * FROM collection_entity")
    fun readAllCollectionEntities(): Flow<List<UserCollectionEntity>>


    @Query("SELECT * FROM Collection_Entity WHERE placeID = (:placeID)")
    fun readCollectionEntity(placeID: String): Flow<UserCollectionEntity>

    @Insert
    fun saveUserCollection(content: UserCollectionEntity)

    @Delete
    fun deleteUserCollection(content: UserCollectionEntity)
}