package com.example.whats_eat.viewModel.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _loginData = MutableLiveData<String>()

    val loginData: LiveData<String>
        get() = _loginData

    fun getUserLogin(userEmail: String, userPassword: String) =
        appRepo.getUser(userEmail, userPassword)
            .addOnCompleteListener{
                if (it.isSuccessful) { _loginData.value = "Success"
                } else { _loginData.value = "Failed" }
            }
            .addOnFailureListener { _loginData.value = it.toString() }
}