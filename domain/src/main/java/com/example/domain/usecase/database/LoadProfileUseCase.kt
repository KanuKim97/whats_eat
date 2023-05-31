package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class LoadProfileUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    fun loadUserProfile(): Unit = DataBaseRepo.loadUserProfile()
}