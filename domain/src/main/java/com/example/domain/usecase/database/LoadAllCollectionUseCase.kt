package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class LoadAllCollectionUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    fun loadAllUserCollection(): Unit = DataBaseRepo.loadUserAllCollection()
}