package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EatDao {
    @Query("SELECT * FROM collection_entity")
    fun readAllCollectionEntities(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM Collection_Entity WHERE placeID = (:placeID)")
    fun readCollectionEntity(placeID: String): Flow<CollectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUserCollection(content: CollectionEntity)

    @Delete(entity = CollectionEntity::class)
    fun deleteUserCollection(content: CollectionEntity)
}