package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FireBaseRTDBRepository
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
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
    private val rtDBRepo: FireBaseRTDBRepository
) : ViewModel() {
    private val userProfile: DatabaseReference by lazy { setUserDBRef() }
    private val userCollection: DatabaseReference by lazy { setUserCollectionRef() }

    private val _userProfileNickName = MutableLiveData<String>()
    private val _userProfileEmail = MutableLiveData<String>()
    private val _userCollectionCnt = MutableLiveData<String>()
    val userProfileNickName: LiveData<String> get() = _userProfileNickName
    val userProfileEmail: LiveData<String> get() = _userProfileEmail
    val userCollectionCnt: LiveData<String> get() = _userCollectionCnt

    private fun setUserDBRef(): DatabaseReference = rtDBRepo.getUserDBRef()
    private fun setUserCollectionRef(): DatabaseReference = rtDBRepo.getUserCollectionDBRef()

    fun loadUserProfile(): ValueEventListener = userProfile
        .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    _userProfileEmail.value = snapshot.child("userEmail").value.toString()
                    _userProfileNickName.value = snapshot.child("userNickName").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })

    fun loadUserCollection(): Task<DataSnapshot> = userCollection.get().addOnCompleteListener {
        if(it.isSuccessful) { _userCollectionCnt.value = it.result.childrenCount.toString() }
        else { it.exception?.printStackTrace() }
    }.addOnFailureListener { it.printStackTrace() }


    fun deleteUserAccount() =
        authRepo.deleteUserAccount()
            ?.addOnCompleteListener {
                if (it.isSuccessful) { rtDBRepo.getUserDBRef().removeValue() }
                else { it.exception?.printStackTrace() }
            }
            ?.addOnFailureListener { it.printStackTrace() }

}