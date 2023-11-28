package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.CreateAccountUseCase
import com.example.domain.usecase.database.SaveProfileUseCase
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.util.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _signInState = MutableStateFlow<AuthState>(AuthState.IsLoading(false))
    val signInState: Flow<AuthState>
        get() = _signInState.asStateFlow()

    fun createAccount(
        userEmail: String,
        userPassword: String,
        userFullName: String,
        userNickName: String
    ): Job = viewModelScope.launch(ioDispatcher) {
        _signInState.value = AuthState.IsLoading(true)

        createAccountUseCase(userEmail, userPassword).collect { result ->
            if (result.isSuccess) {
                saveUserInfo(userEmail, userFullName, userNickName)
                _signInState.value = AuthState.AuthResult(true)
            } else {
                _signInState.value = AuthState.AuthResult(false)
            }
        }

        _signInState.value = AuthState.IsLoading(false)
    }

    private fun saveUserInfo(
        userEmail: String,
        userFullName: String,
        userNickName: String
    ): Job = viewModelScope.launch(ioDispatcher) {
        saveProfileUseCase.saveUserProfile(userEmail, userNickName, userFullName)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel(cause = CancellationException("Coroutine Job is Cancelled"))
    }
}
