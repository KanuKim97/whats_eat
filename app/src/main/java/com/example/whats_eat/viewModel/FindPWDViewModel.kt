package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.SendResetEmailUseCase
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindPWDViewModel @Inject constructor(
    private val sendResetEmailUseCase: SendResetEmailUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _sendResetEmail = MutableLiveData<Result<Unit>>()
    val sendResetEmail: LiveData<Result<Unit>> get() = _sendResetEmail

    fun sendPasswordResetEmail(userEmail: String) = viewModelScope.launch(ioDispatcher) {
        sendResetEmailUseCase.sendPasswordResetEmail(userEmail).collect {
            _sendResetEmail.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}