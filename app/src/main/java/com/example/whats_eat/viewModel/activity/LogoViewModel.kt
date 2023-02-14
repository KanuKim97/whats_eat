package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogoViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    private val _readFireAuth = MutableLiveData<Boolean>()
    val readFireAuth: LiveData<Boolean>
        get() = _readFireAuth

    fun checkUserSession() = _readFireAuth.postValue(firebaseRepo.getCurrentUserSession())
}
