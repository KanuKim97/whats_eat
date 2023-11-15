package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrentUserUseCase @Inject constructor(
    private val authRepo: AuthRepository
){
    fun getCurrentUser(): Flow<FirebaseUser?> = authRepo.getCurrentUser()
}