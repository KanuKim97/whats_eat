package com.example.data.repository

import com.example.data.model.CollectionDataModel
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    fun readAllCollectionEntities(): Flow<List<CollectionDataModel>>

    fun readCollectionEntity(placeId: String): Flow<CollectionDataModel>

    fun saveUserCollection(
        placeId: String,
        placeName: String,
        placeImgUrl: String,
        placeLatLng: String
    ): Flow<Result<Unit>>

    fun deleteUserCollection(content: CollectionDataModel): Flow<Result<Unit>>
}