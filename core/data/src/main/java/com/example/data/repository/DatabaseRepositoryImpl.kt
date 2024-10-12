package com.example.data.repository

import com.example.common.IODispatcher
import com.example.data.mapper.dataModelToEntityMapper
import com.example.data.mapper.entityToDataModelMapper
import com.example.data.model.CollectionDataModel
import com.example.database.dao.EatDao
import com.example.database.model.CollectionEntity
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
    override fun readAllCollectionEntities(): Flow<List<CollectionDataModel>> = eatDao
        .readAllCollectionEntities()
        .map { it.map { entity -> entityToDataModelMapper(entity) } }
        .catch { exception ->
            when (exception) {
                is IOException -> throw IOException()
                is SQLDataException -> throw SQLDataException()
                is ClassNotFoundException -> throw ClassNotFoundException()
                else -> throw exception
            }
        }.flowOn(ioDispatcher)

    override fun readCollectionEntity(placeId: String): Flow<CollectionDataModel> = eatDao
        .readCollectionEntity(placeId)
        .map { entityToDataModelMapper(it) }
        .catch { exception ->
            when (exception) {
                is IOException -> throw IOException()
                is SQLDataException -> throw SQLDataException()
                is ClassNotFoundException -> throw ClassNotFoundException()
                else -> throw exception
            }
        }.flowOn(ioDispatcher)

    override fun saveUserCollection(
        placeId: String,
        placeName: String,
        placeImgUrl: String,
        placeLatLng: String
    ): Flow<Result<Unit>> = flow {
        val entity = CollectionEntity(
            placeID = placeId,
            placeName = placeName,
            placeImgUrl = placeImgUrl,
            placeLatLng = placeLatLng
        )


        eatDao.saveUserCollection(entity)
        emit(Result.success(Unit))
    }.catch { exception ->
        when (exception) {
            is IOException -> emit(Result.failure(exception))
            is SQLDataException -> emit(Result.failure(exception))
            is ClassNotFoundException -> emit(Result.failure(exception))
            else -> emit(Result.failure(exception))
        }
    }.flowOn(ioDispatcher)

    override fun deleteUserCollection(content: CollectionDataModel): Flow<Result<Unit>> = flow {
        val entity = dataModelToEntityMapper(content)

        eatDao.deleteUserCollection(entity)
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