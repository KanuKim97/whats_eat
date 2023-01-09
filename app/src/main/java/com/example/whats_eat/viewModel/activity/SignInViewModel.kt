package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository

class SignInViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _signInResult = MutableLiveData<Boolean>()

    val signInResult: LiveData<Boolean>
        get() = _signInResult

    fun createUserAccount(
        userEmail: String,
        userPassword: String,
        fullName: String,
        userName: String
    ) {
        appRepo.createUser(userEmail, userPassword)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    setUserDBValue(userEmail, userName, fullName)
                    _signInResult.value = true
                } else { _signInResult.value = false }
            }
            .addOnFailureListener { _signInResult.value = false }
    }

    private fun setUserDBValue(eMail: String, nickName: String, fullName: String) {
        val currentUserRef = appRepo.getUserData()

        currentUserRef.child("eMail").setValue(eMail)
        currentUserRef.child("NickName").setValue(nickName)
        currentUserRef.child("fullName").setValue(fullName)
    }
}