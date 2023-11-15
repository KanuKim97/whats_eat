package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val authRepo: AuthRepository
) {
    fun deleteUserAccount(): Flow<Result<Unit>> = authRepo.deleteUserAccount()
}