package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.LogInAccountUseCase
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInAccountUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _logInResult = MutableLiveData<Result<Unit>>()
    val logInResult: LiveData<Result<Unit>> get() = _logInResult

    fun userLogIn(userEmail: String, userPassword: String) = viewModelScope.launch(ioDispatcher) {
        logInUseCase.logInUserAccount(userEmail, userPassword).collect {
            _logInResult.postValue(it)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}