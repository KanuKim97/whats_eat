package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.FirebaseAuthProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPwViewModel @Inject constructor(
    private val authProducer: FirebaseAuthProducer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _sendResetEmail = MutableLiveData<Result<Unit>>()
    val sendResetEmail: LiveData<Result<Unit>> get() = _sendResetEmail

    fun sendPasswordResetEmail(userEmail: String) = viewModelScope.launch(ioDispatcher) {
        authProducer.sendPasswordResetEmail(userEmail).collect {
            _sendResetEmail.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}