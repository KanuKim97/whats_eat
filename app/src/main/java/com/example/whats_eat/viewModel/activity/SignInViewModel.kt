package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    private val _isNewUserResult = MutableLiveData<Boolean>()
    val isNewUserResult: LiveData<Boolean> get() = _isNewUserResult

    fun createUserAccount(
        userEmail: String,
        userPassword: String,
        userFullName: String,
        userNickName: String
    ) {
        firebaseRepo.createUserAccount(userEmail, userPassword)
            .addOnCompleteListener{
                val isNewUser: Boolean = it.result.additionalUserInfo?.isNewUser!!

                when {
                    isNewUser && it.isSuccessful -> {
                        _isNewUserResult.value = true
                        setUserInfoInDB(userEmail, userNickName, userFullName)
                    }
                    (!it.isSuccessful) -> it.exception?.printStackTrace()
                    (!isNewUser) -> _isNewUserResult.value = false
                }
            }
            .addOnFailureListener { it.printStackTrace() }
    }

    private fun setUserInfoInDB(userEmail: String, userNickName: String, userFullName: String) {
        val currentUserDBRef = firebaseRepo.getUserProfileDBPath()

        currentUserDBRef.child("userEmail").setValue(userEmail)
        currentUserDBRef.child("userNickName").setValue(userNickName)
        currentUserDBRef.child("userFullName").setValue(userFullName)
    }
}