package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    private val _userLogInResult = MutableLiveData<Task<AuthResult>>()
    val userLogInResult: LiveData<Task<AuthResult>> get() = _userLogInResult

    fun logInUserAccount(userEmail: String, userPassword: String) =
        firebaseRepo.signInUserAccount(userEmail, userPassword)
            .addOnCompleteListener {
                if (it.isSuccessful) { _userLogInResult.value = it }
                else { it.exception?.printStackTrace() }
            }
            .addOnFailureListener { it.printStackTrace() }
}