package com.example.domain.usecase.database

import com.example.domain.model.CollectionItem
import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveCollectionUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    suspend fun saveUserCollection(
        collection: CollectionItem
    ): Unit = DataBaseRepo.saveUserCollection(collection)
}