package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FireBaseRTDBRepository
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepository,
    private val rtDBRepo: FireBaseRTDBRepository
): ViewModel() {
    private val userProfile: DatabaseReference by lazy { setUserDBRef() }

    private val _userEmail = MutableLiveData<String>()
    private val _userFullName = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail
    val userFullName: LiveData<String> get() = _userFullName

    private fun setUserDBRef(): DatabaseReference = rtDBRepo.getUserDBRef()

    fun getUserAccountData(): ValueEventListener = userProfile
        .addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    _userFullName.value = snapshot.child("userFullName").value.toString()
                    _userEmail.value = snapshot.child("userEmail").value.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })

    fun signOutUserAccount(): Unit = authRepo.signOutUserAccount()
}