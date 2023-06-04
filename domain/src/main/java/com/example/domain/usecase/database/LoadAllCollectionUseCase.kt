package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class LoadAllCollectionUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    val collectionItems = DataBaseRepo.collectionItem

    fun loadAllUserCollection(): Unit = DataBaseRepo.loadUserAllCollection()
}