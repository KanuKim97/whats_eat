package com.example.data.repository

import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    override fun getCurrentUser(): Flow<FirebaseUser?> = callbackFlow {
        auth.addAuthStateListener { userState -> trySend(userState.currentUser) }
        awaitClose()
    }

    override fun createUserAccount(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>> = flow {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).await()
        emit(Result.success(Unit))
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    override fun logInUserAccount(
        userEmail: String,
        userPassword: String
    ): Flow<Result<Unit>> = flow {
        auth.signInWithEmailAndPassword(userEmail, userPassword).await()
        emit(Result.success(Unit))
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    override fun sendPasswordResetEmail(userEmail: String): Flow<Result<Unit>> = flow {
        auth.sendPasswordResetEmail(userEmail)
        emit(Result.success(Unit))
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    override fun deleteUserAccount(): Flow<Result<Unit>> = flow {
        auth.currentUser?.delete()?.await()
        emit(Result.success(Unit))
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    override fun signOutUserAccount(): Flow<Boolean> = callbackFlow<Boolean> {
        auth.signOut()
        send(true)
        awaitClose()
    }.catch { exception ->
        when (exception) {
            is IOException -> emit(false)
            is IllegalStateException -> emit(false)
            else -> throw exception
        }
    }

}