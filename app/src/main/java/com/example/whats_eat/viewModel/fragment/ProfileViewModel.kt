package com.example.whats_eat.viewModel.fragment

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
        val userProfileRef = firebaseRepo.getUserProfileDBPath()
        val userCollection = firebaseRepo.getUserDBCollectionPath()

        userProfileRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _userProfileEmail.value = snapshot.child("userEmail").value.toString()
                    _userProfileNickName.value = snapshot.child("userNickName").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })

        userCollection.get()
            .addOnCompleteListener {
                if(it.isSuccessful) { _userCollectionCnt.value = it.result.childrenCount.toString() }
                else { it.exception?.printStackTrace() }
            }
            .addOnFailureListener { it.printStackTrace() }
    }

    fun deleteUserAccount() =
        firebaseRepo.deleteUserAccount()
            ?.addOnCompleteListener {
                if (it.isSuccessful) { firebaseRepo.getUserDBCollectionPath().removeValue() }
                else { it.exception?.printStackTrace() }
            }
            ?.addOnFailureListener { it.printStackTrace() }

}