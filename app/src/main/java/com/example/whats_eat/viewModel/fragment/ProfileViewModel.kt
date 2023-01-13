package com.example.whats_eat.viewModel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class ProfileViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _profileNickName = MutableLiveData<String>()
    private val _profileEmail = MutableLiveData<String>()
    private val _collectionCnt = MutableLiveData<String>()

    val profileNickName: LiveData<String>
        get() = _profileNickName
    val profileEmail: LiveData<String>
        get() = _profileEmail
    val collectionCnt: LiveData<String>
        get() = _collectionCnt

    fun loadUserProfile() {
        val userProfileRef = appRepo.getUserData()
        val userCollectionCnt = appRepo.getCollectionPath()

        userProfileRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    _profileNickName.value = snapshot.child("NickName").value.toString()
                    _profileEmail.value = snapshot.child("eMail").value.toString()
                }
            }
            override fun onCancelled(error: DatabaseError) { Log.e("Error",  error.message) }
        })

        userCollectionCnt.get()
            .addOnCompleteListener {
                if(it.isSuccessful) { _collectionCnt.value = it.result.childrenCount.toString() }
            }
            .addOnFailureListener {  }
    }

    fun deleteAccount() =
        appRepo.deleteUserAccount()?.addOnCompleteListener {
            if (it.isSuccessful) { appRepo.getUserData().removeValue() }
        }

}