package com.example.domain.database

import com.example.data.repository.DatabaseRepository
import com.example.model.collection.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAllCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(): Flow<List<CollectionModel>> = dbRepository.readAllCollectionEntities()
}