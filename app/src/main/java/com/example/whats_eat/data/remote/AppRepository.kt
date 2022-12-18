package com.example.whats_eat.data.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AppRepository {
    private val placeApiService: IGoogleAPIService =
        RetrofitClient
            .getClient()
            .create(IGoogleAPIService::class.java)

    private val firebaseDB = FirebaseDatabase.getInstance()
    private val firebaseDBRef = firebaseDB.reference.child("userInfo")

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

    fun getUserData(userUID: String) {
        firebaseDBRef
            .child(userUID)
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()) {
                        val eMail: String = snapshot.child("eMail").value.toString()
                        val userName: String = snapshot.child("userName").value.toString()
                        val fullName: String = snapshot.child("fullName").value.toString()
                    }
                }

                override fun onCancelled(error: DatabaseError) {  }
        })
    }

}