package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogInAccountUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    fun logInUserAccount(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>> = authRepo.logInUserAccount(userEmail, userPassword)

    operator fun invoke(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>> = authRepo.logInUserAccount(userEmail, userPassword)
}