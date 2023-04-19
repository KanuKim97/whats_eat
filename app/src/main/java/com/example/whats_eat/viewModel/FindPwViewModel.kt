package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseAuthRepository
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(
    private val authRepo: FirebaseAuthRepository
): ViewModel() {
    private val _eMailReset = MutableLiveData<Boolean>()
    val eMailReset: LiveData<Boolean> get() = _eMailReset

    fun sendUserPasswordResetEmail(userEmail: String): Task<Void> = authRepo.findUserAccountPassword(userEmail)
        .addOnCompleteListener {
            if(it.isSuccessful) {
                _eMailReset.value = true
            } else {
                it.exception?.printStackTrace()
                _eMailReset.value = false
            }
        }
        .addOnFailureListener {
            _eMailReset.value = false
            it.printStackTrace()
        }

}