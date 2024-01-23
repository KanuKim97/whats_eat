package com.example.domain

import com.example.data.repository.DatabaseRepository
import com.example.model.feature.CollectionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteCollectionUseCase @Inject constructor(
    private val dbRepository: DatabaseRepository
) {
    operator fun invoke(
        content: CollectionModel
    ): Flow<Result<Unit>> = dbRepository.deleteUserCollection(content)
}