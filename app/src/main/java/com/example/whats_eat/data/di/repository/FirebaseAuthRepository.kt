package com.example.whats_eat.data.di.repository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/* Firebase Auth Service Repository */
class FirebaseAuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUserSession() = firebaseAuth.currentUser != null
    fun createUserAccount(userEmail: String, userPassword: String) =
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)

    fun signInUserAccount(userEmail: String, userPassword: String) =
        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)

    fun findUserAccountPassword(userEmail: String) =
        firebaseAuth.sendPasswordResetEmail(userEmail)

    fun deleteUserAccount() = firebaseAuth.currentUser?.delete()

    fun signOutUserAccount() = firebaseAuth.signOut()
}