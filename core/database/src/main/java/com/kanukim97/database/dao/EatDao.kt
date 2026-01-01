package com.kanukim97.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kanukim97.database.model.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EatDao {
    @Query("SELECT * FROM collection_entity")
    fun readAllCollections(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM Collection_Entity WHERE id = (:placeID)")
    fun readCollection(placeID: String): Flow<CollectionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCollection(content: CollectionEntity)

    @Query("DELETE FROM collection_entity WHERE id = (:placeID)")
    suspend fun deleteCollectionById(placeID: String)
}