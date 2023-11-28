package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val dataBaseRepo: DataBaseRepository
) {
    /* TODO("Need To Fix") */

    suspend fun saveUserProfile(
        userEmail: String,
        userNickName: String,
        userFullName: String
    ): Unit = dataBaseRepo.saveUserProfile(userEmail, userNickName, userFullName)
}