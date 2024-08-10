package com.example.domain.database

import com.example.data.repository.DatabaseRepository
import com.example.model.domain.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(
        content: CollectionModel
    ): Flow<Result<Unit>> = dbRepository.saveUserCollection(content)
}