package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FireBaseDBRepository
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
import com.example.whats_eat.view.dataViewClass.ProfileClass
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepository,
    private val rtDBRepo: FireBaseDBRepository
) : ViewModel() {
    private val userProfileRef: DatabaseReference = rtDBRepo.getUserDBRef()
    private val userCollectionRef: DatabaseReference = rtDBRepo.getUserCollectionDBRef()

    private val _userProfile = MutableLiveData<ProfileClass>()
    private val _userCollectionCnt = MutableLiveData<String>()

    val userProfile: LiveData<ProfileClass> get() = _userProfile
    val userCollectionCnt: LiveData<String> get() = _userCollectionCnt

    init {
        loadUserProfile()
        loadUserCollection()
    }

    private fun loadUserProfile(): ValueEventListener =
        userProfileRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userEmail = snapshot.child("userEmail").value.toString()
                    val userName = snapshot.child("userNickName").value.toString()

                    _userProfile.value = ProfileClass(userName, userEmail)
                }
            }
            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })

    private fun loadUserCollection(): Task<DataSnapshot> = userCollectionRef.get()
        .addOnCompleteListener {
            if(it.isSuccessful) {
                _userCollectionCnt.value = it.result.childrenCount.toString()
            } else {
                it.exception?.printStackTrace()
            }
        }.addOnFailureListener { it.printStackTrace() }

    fun deleteUserAccount(): Task<Void>? = authRepo.deleteUserAccount()?.addOnCompleteListener {
        if (it.isSuccessful) {
            rtDBRepo.getUserDBRef().removeValue()
        } else {
            it.exception?.printStackTrace()
        }
    }?.addOnFailureListener { it.printStackTrace() }

}