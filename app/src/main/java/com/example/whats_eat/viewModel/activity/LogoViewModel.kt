package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository

class LogoViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _readFireAuth = MutableLiveData<Boolean>()

    val readFireAuth: LiveData<Boolean>
        get() = _readFireAuth

    fun checkUserSession() { _readFireAuth.value = appRepo.getUserSession() }
}
