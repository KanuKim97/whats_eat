package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FireBaseRTDBRepository
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepository,
    private val rtDBRepo: FireBaseRTDBRepository
): ViewModel() {
    private val userProfile: DatabaseReference by lazy { setUserDBRef() }

    private val _isNewUserResult = MutableLiveData<Boolean>()
    val isNewUserResult: LiveData<Boolean> get() = _isNewUserResult

    private fun setUserDBRef(): DatabaseReference = rtDBRepo.getUserDBRef()

    fun createUserAccount(
        userEmail: String,
        userPassword: String,
        userFullName: String,
        userNickName: String
    ) {
        authRepo.createUserAccount(userEmail, userPassword)
            .addOnCompleteListener{
                val isNewUser: Boolean = it.result.additionalUserInfo?.isNewUser!!

                when {
                    isNewUser && it.isSuccessful -> {
                        _isNewUserResult.value = true
                        setUserProfileInDB(userEmail, userNickName, userFullName)
                    }
                    !it.isSuccessful -> it.exception?.printStackTrace()
                    !isNewUser -> _isNewUserResult.value = false
                }
            }
            .addOnFailureListener { it.printStackTrace() }
    }

    private fun setUserProfileInDB(userEmail: String, userNickName: String, userFullName: String) {
        userProfile.child("userEmail").setValue(userEmail)
        userProfile.child("userNickName").setValue(userNickName)
        userProfile.child("userFullName").setValue(userFullName)
    }
}