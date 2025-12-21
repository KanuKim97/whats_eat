package com.kanukim97.data.impl

import com.kanukim97.data.mapper.entityToModelMapper
import com.kanukim97.data.model.PlaceCollection
import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.database.dao.EatDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.sql.SQLDataException
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(private val eatDao: EatDao): DatabaseRepository {
    override fun readAllCollections(): Flow<List<PlaceCollection>> = eatDao
        .readAllCollections()
        .map { entities ->
            entities.map { entity -> entityToModelMapper(entity) }
        }
        .catch { exception ->
            when (exception) {
                is IOException -> emit(listOf())
                is SQLDataException -> emit(listOf())
                is ClassNotFoundException -> emit(listOf())
                else -> emit(listOf())
            }
        }

    override fun readCollection(placeID: String): Flow<PlaceCollection> = eatDao
        .readCollection(placeID)
        .map { entity -> entityToModelMapper(entity) }




    override suspend fun deleteCollection(id: String) {
        eatDao.deleteCollectionById(id)
    }
}