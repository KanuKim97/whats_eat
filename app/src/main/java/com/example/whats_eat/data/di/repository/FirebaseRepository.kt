package com.example.whats_eat.data.di.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseRepository {
    private val fireAuth = FirebaseAuth.getInstance()
    private val fireRealDB = FirebaseDatabase.getInstance()
    private val fireRealDBRef = fireRealDB.reference.child(fireAuth.uid.toString())

    fun getCurrentUserSession() = fireAuth.currentUser != null

    fun createUserAccount(userEmail: String, userPassword: String) =
        fireAuth.createUserWithEmailAndPassword(userEmail, userPassword)

    fun signInUserAccount(userEmail: String, userPassword: String) =
        fireAuth.signInWithEmailAndPassword(userEmail, userPassword)

    fun findUserAccountPassword(userEmail: String) =
        fireAuth.sendPasswordResetEmail(userEmail)

    fun deleteUserAccount() = fireAuth.currentUser?.delete()

    fun signOutUserAccount() = fireAuth.signOut()

    fun getUserProfileDBPath() = fireRealDBRef

    fun getUserDBCollectionPath() =
        fireRealDBRef.child("Collection")
}