package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendResetEmailUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    fun sendPasswordResetEmail(
        userEmail: String
    ): Flow<Result<Unit>> = authRepo.sendPasswordResetEmail(userEmail)

    operator fun invoke(
        userEmail: String
    ): Flow<Result<Unit>> = authRepo.sendPasswordResetEmail(userEmail)
}