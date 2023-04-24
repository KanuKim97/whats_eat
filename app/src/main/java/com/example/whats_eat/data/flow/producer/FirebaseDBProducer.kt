package com.example.whats_eat.data.flow.producer

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FirebaseDBProducer @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseDatabase
) {
    /*
    fun getUserDataBaseReference(): Flow<DatabaseReference> = flow {
        emit(
            firebaseDB
                .reference
                .child(firebaseAuth.currentUser?.uid.toString())
        )
    }

    fun getUserCollectionReference(): Flow<DatabaseReference> = flow {
        emit(
            firebaseDB
                .reference
                .child(firebaseAuth.currentUser?.uid.toString())
                .child("collection")
        )
    }
    */


}