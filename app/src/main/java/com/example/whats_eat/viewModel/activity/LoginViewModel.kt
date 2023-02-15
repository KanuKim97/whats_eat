package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {

    fun logInUserAccount(userEmail: String, userPassword: String) =
        firebaseRepo.signInUserAccount(userEmail, userPassword)
            .addOnCompleteListener {  }
            .addOnFailureListener {  }
}