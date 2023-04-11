package com.example.whats_eat.data.di.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/* Firebase Auth Service Repository */
class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUserSession(): Boolean = firebaseAuth.currentUser != null

    fun createUserAccount(userEmail: String, userPassword: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)

    fun signInUserAccount(userEmail: String, userPassword: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)

    fun findUserAccountPassword(userEmail: String): Task<Void> =
        firebaseAuth.sendPasswordResetEmail(userEmail)

    fun deleteUserAccount(): Task<Void>? = firebaseAuth.currentUser?.delete()

    fun signOutUserAccount(): Unit = firebaseAuth.signOut()
}