package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository

class FindPwViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _emailResetValue = MutableLiveData<Boolean>()

    val emailResetValue: LiveData<Boolean>
        get() = _emailResetValue

    fun sendPasswordResetMail(userEmail: String) {
        appRepo.findPassword(userEmail = userEmail)
            .addOnCompleteListener { if (it.isSuccessful) { _emailResetValue.value = true } }
            .addOnFailureListener { _emailResetValue.value = false }
    }
}