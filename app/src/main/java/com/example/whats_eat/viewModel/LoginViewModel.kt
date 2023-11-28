package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.LogInAccountUseCase
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.util.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUseCase: LogInAccountUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _userLogInResult = MutableStateFlow<AuthState>(AuthState.IsLoading(false))
    val userLogInResult: Flow<AuthState>
        get() = _userLogInResult.asStateFlow()

    fun userLogIn(userEmail: String, userPassword: String) = viewModelScope.launch(ioDispatcher) {
        _userLogInResult.value = AuthState.IsLoading(true)

        logInUseCase(userEmail, userPassword).collect { result ->
            if (result.isSuccess) {
                _userLogInResult.value = AuthState.AuthResult(true)
            } else {
                _userLogInResult.value = AuthState.AuthResult(false)
            }
        }

        _userLogInResult.value = AuthState.IsLoading(false)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel(cause = CancellationException("Coroutine Job is Cancelled"))
    }
}