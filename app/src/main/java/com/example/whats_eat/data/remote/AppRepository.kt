package com.example.whats_eat.data.remote

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AppRepository {
    private val placeApiService: IGoogleAPIService =
        RetrofitClient
            .getClient()
            .create(IGoogleAPIService::class.java)

    private val fireAuth = FirebaseAuth.getInstance()
    private val firebaseDB = FirebaseDatabase.getInstance()
    private val firebaseDBRef = firebaseDB.reference.child("userInfo")

    fun getUserSession(): Boolean = fireAuth.currentUser != null

    fun getUser(userEmail: String, userPassword: String): Task<AuthResult> =
        fireAuth.signInWithEmailAndPassword(userEmail, userPassword)

    fun createUser(userEmail: String, userPassword: String) =
        fireAuth.createUserWithEmailAndPassword(userEmail, userPassword)

    fun findPassword(userEmail: String) = fireAuth.sendPasswordResetEmail(userEmail)

    fun userSignOut() = fireAuth.signOut()

    suspend fun getNearby(
        latLng: String,
        radius: String,
        type: String,
        Api_key: String
    ) = placeApiService.getNearPlaces(latLng, radius, type, Api_key)

    suspend fun getDetailPlace(
        place_ID: String,
        Api_key: String
    ) = placeApiService.getPlaceDetail(place_ID, Api_key)

    fun getUserData() = firebaseDBRef.child(fireAuth.currentUser?.uid!!)

    fun getCollectionPath() =
        firebaseDBRef.child(fireAuth.currentUser?.uid!!).child("collection")
}