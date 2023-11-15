package com.example.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getCurrentUser(): Flow<FirebaseUser?>

    fun createUserAccount(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>>

    fun logInUserAccount(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>>

    fun sendPasswordResetEmail(userEmail: String): Flow<Result<Unit>>

    fun deleteUserAccount(): Flow<Result<Unit>>

    fun signOutUserAccount(): Flow<Boolean>
}