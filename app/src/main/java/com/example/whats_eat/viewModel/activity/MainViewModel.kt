package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class MainViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    fun getUserAccountData() {
        val userInfoRef = firebaseRepo.getUserDBCollectionPath()

        userInfoRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val userFullName = snapshot.child("userFullName").value.toString()
                    val userEmail = snapshot.child("userEmail").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })
    }
    fun signOutUserAccount() = firebaseRepo.signOutUserAccount()
}