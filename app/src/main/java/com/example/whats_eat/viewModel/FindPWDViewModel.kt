package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.SendResetEmailUseCase
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
class FindPWDViewModel @Inject constructor(
    private val sendResetEmailUseCase: SendResetEmailUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _sendEmailState = MutableStateFlow<AuthState>(AuthState.IsLoading(false))
    val sendEmailState: Flow<AuthState>
        get() = _sendEmailState.asStateFlow()

    fun sendPWDResetEmail(userEmail: String) = viewModelScope.launch(ioDispatcher) {
        _sendEmailState.value = AuthState.IsLoading(true)

        sendResetEmailUseCase(userEmail).collect { result ->
            if (result.isSuccess) {
                _sendEmailState.value = AuthState.AuthResult(true)
            } else {
                _sendEmailState.value = AuthState.AuthResult(false)
            }
        }

        _sendEmailState.value = AuthState.IsLoading(false)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel(cause = CancellationException("Coroutine Job is Cancelled"))
    }
}