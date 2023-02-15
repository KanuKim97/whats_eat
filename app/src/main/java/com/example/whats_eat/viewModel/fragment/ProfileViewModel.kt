package com.example.whats_eat.viewModel.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository) : ViewModel() {
    private val _userProfileNickName = MutableLiveData<String>()
    private val _userProfileEmail = MutableLiveData<String>()
    private val _userCollectionCnt = MutableLiveData<String>()

    val userProfileNickName: LiveData<String> get() = _userProfileNickName
    val userProfileEmail: LiveData<String> get() = _userProfileEmail
    val userCollectionCnt: LiveData<String> get() = _userCollectionCnt
    fun loadUserProfile() {
        val userProfileRef = firebaseRepo.getUserDBCollectionPath()

    }

    fun deleteUserAccount() =
        firebaseRepo.deleteUserAccount()
            ?.addOnCompleteListener {
                if (it.isSuccessful) { firebaseRepo.getUserDBCollectionPath().removeValue() }
                else { it.exception?.printStackTrace() }
            }
            ?.addOnFailureListener { it.printStackTrace() }

    /*
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
*/

}