package com.kanukim97.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kanukim97.database.dao.EatDao
import com.kanukim97.database.model.CollectionEntity

@Database(
    entities = [CollectionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EatDataBase: RoomDatabase() {
    abstract fun eatDao(): EatDao
}