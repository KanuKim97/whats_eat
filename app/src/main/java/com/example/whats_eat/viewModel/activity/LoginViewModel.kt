package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepository
): ViewModel() {
    private val _userLogInResult = MutableLiveData<Task<AuthResult>>()
    val userLogInResult: LiveData<Task<AuthResult>> get() = _userLogInResult

    fun logInUserAccount(userEmail: String, userPassword: String): Task<AuthResult> =
        authRepo.signInUserAccount(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    _userLogInResult.value = task
                } else {
                    task.exception?.printStackTrace()
                }
            }
            .addOnFailureListener { it.printStackTrace() }
}