package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogOutAccountUseCase @Inject constructor(
    private val authRepo: AuthRepository
){
    fun logOutUserAccount(): Flow<Boolean> = authRepo.signOutUserAccount()
}