package com.kanukim97.domain.database

import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.model.domain.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(
        placeID: String
    ): Flow<CollectionModel> = dbRepository.readCollectionEntity(placeID)
}