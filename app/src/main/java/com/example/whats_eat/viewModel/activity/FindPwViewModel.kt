package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    private val _eMailReset = MutableLiveData<Boolean>()
    val eMailReset: LiveData<Boolean> get() = _eMailReset

    fun sendUserPasswordResetEmail(userEmail: String) {
        firebaseRepo.findUserAccountPassword(userEmail)
            .addOnCompleteListener {
                if(it.isSuccessful) { _eMailReset.value = true }
                else {
                    it.exception?.printStackTrace()
                    _eMailReset.value = false
                }
            }
            .addOnFailureListener {
                _eMailReset.value = false
                it.printStackTrace()
            }
    }
}