package com.example.whats_eat.data.flow.producer

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class FirebaseAuthProducer @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUser(): Flow<FirebaseUser?> = flow {
        emit(firebaseAuth.currentUser)
    }.catch { exception ->
        if (exception is IOException) {
            emit(null)
        } else {
            throw exception
        }
    }

    fun createUserAccount(eMail: String, password: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.createUserWithEmailAndPassword(eMail, password).await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    fun signInUserAccount(eMail: String, password: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.signInWithEmailAndPassword(eMail, password).await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    fun sendPasswordResetEmail(eMail: String): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.sendPasswordResetEmail(eMail).await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }

    fun deleteUserAccount(): Flow<Result<Unit>> = flow {
        try {
            firebaseAuth.currentUser?.delete()?.await()
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(Result.failure(exception))
        } else {
            throw exception
        }
    }
}