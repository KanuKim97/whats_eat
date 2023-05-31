package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    operator fun invoke(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>> = authRepo.createUserAccount(userEmail, userPassword)
}