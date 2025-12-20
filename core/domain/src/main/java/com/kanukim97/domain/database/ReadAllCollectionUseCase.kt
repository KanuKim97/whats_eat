package com.kanukim97.domain.database

import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.model.domain.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<List<CollectionModel>> = dbRepository.readAllCollectionEntities()
}