package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.dao.EatDao
import com.example.database.model.CollectionEntity

@Database(
    entities = [CollectionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EatDataBase: RoomDatabase() {
    abstract fun eatDao(): EatDao
}