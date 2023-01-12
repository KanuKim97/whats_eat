package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileViewModel(private val appRepo: AppRepository): ViewModel() {


    fun loadUserProfile() {
        val userProfileRef = appRepo.getUserData()
        val userCollectionCnt = appRepo.getCollectionPath()

        userProfileRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    snapshot.child("NickName").value.toString()
                    snapshot.child("eMail").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) { TODO("Not yet implemented") }
        })

        userCollectionCnt.get()
            .addOnCompleteListener {
                if(it.isSuccessful) { it.result.childrenCount.toString() }
            }
            .addOnFailureListener {  }
    }

    fun deleteAccount() =
        appRepo.deleteUserAccount()?.addOnCompleteListener {
            if (it.isSuccessful) { appRepo.getUserData().removeValue() }
        }

}