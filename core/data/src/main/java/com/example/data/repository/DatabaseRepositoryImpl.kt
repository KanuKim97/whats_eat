package com.example.data.repository

import com.example.common.IODispatcher
import com.example.data.util.entityToModelMapper
import com.example.data.util.modelToEntityMapper
import com.example.model.feature.CollectionModel
import com.example.database.dao.EatDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.sql.SQLDataException
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eatDao: EatDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): DatabaseRepository {
    override fun readAllCollectionEntities() = eatDao
        .readAllCollectionEntities()
        .map { entityList ->
            entityList.map { entity -> entityToModelMapper(entity) }
        }
        .catch { exception ->
            when (exception) {
                is IOException -> emit(listOf())
                is SQLDataException -> emit(listOf())
                is ClassNotFoundException -> emit(listOf())
                else -> emit(listOf())
            }
        }.flowOn(ioDispatcher)

    override fun readCollectionEntity(
        placeID: String
    ): Flow<CollectionModel> = eatDao
        .readCollectionEntity(placeID)
        .map { entity -> entityToModelMapper(entity) }
        .flowOn(ioDispatcher)

    override fun saveUserCollection(
        content: CollectionModel
    ): Flow<Result<Unit>> = flow {
        eatDao.saveUserCollection(modelToEntityMapper(content))
        emit(Result.success(Unit))
    }.catch { exception ->
        when (exception) {
            is IOException -> emit(Result.failure(exception))
            is SQLDataException -> emit(Result.failure(exception))
            is ClassNotFoundException -> emit(Result.failure(exception))
            else -> emit(Result.failure(exception))
        }
    }.flowOn(ioDispatcher)

    override fun deleteUserCollection(
        content: CollectionModel
    ): Flow<Result<Unit>> = flow {
        eatDao.deleteUserCollection(modelToEntityMapper(content))
        emit(Result.success(Unit))
    }.catch { exception ->
        when (exception) {
            is IOException -> emit(Result.failure(exception))
            is SQLDataException -> emit(Result.failure(exception))
            is ClassNotFoundException -> emit(Result.failure(exception))
            else -> emit(Result.failure(exception))
        }
    }.flowOn(ioDispatcher)
}