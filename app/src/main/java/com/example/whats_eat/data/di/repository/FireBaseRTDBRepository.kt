package com.example.whats_eat.data.di.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

/* FireBase RealTime Database Service Repository */
class FireBaseRTDBRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseDatabase
) {
    fun getUserDBRef(): DatabaseReference =
        firebaseDB.reference.child(firebaseAuth.currentUser?.uid.toString())

    fun getUserCollectionDBRef(): DatabaseReference =
        firebaseDB
            .reference
            .child(firebaseAuth.currentUser?.uid.toString())
            .child("collection")
}