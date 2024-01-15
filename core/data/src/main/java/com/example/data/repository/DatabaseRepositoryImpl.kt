package com.example.data.repository

import com.example.common.IODispatcher
import com.example.database.dao.EatDao
import com.example.database.model.UserCollectionEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import java.sql.SQLDataException
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val eatDao: EatDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): DatabaseRepository {
    override fun readAllCollectionEntities(): Flow<List<UserCollectionEntity>> =
        eatDao.readAllCollectionEntities().flowOn(ioDispatcher)

    override fun readCollectionEntity(
        placeID: String
    ): Flow<UserCollectionEntity> = eatDao.readCollectionEntity(placeID).flowOn(ioDispatcher)

    override fun saveUserCollection(
        content: UserCollectionEntity
    ): Flow<Result<Unit>> = flow {
        eatDao.saveUserCollection(content)
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
        content: UserCollectionEntity
    ): Flow<Result<Unit>> = flow {
        eatDao.deleteUserCollection(content)
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