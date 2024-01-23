package com.example.domain

import com.example.data.repository.DatabaseRepository
import com.example.model.feature.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(
        placeID: String
    ): Flow<CollectionModel> = dbRepository.readCollectionEntity(placeID)
}