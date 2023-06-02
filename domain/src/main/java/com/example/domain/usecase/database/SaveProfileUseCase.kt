package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    suspend fun saveUserProfile(
        userEmail: String,
        userNickName: String,
        userFullName: String
    ): Unit = DataBaseRepo.saveUserProfile(userEmail, userNickName, userFullName)
}