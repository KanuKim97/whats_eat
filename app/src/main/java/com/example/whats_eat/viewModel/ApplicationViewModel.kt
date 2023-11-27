package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.CurrentUserUseCase
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ApplicationViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _userState = MutableStateFlow(false)
    val userState: StateFlow<Boolean> get() = _userState.asStateFlow()

    init { checkUserState() }

    private fun checkUserState(): Job = viewModelScope.launch(ioDispatcher) {
        currentUserUseCase().collect { userState -> _userState.value = (userState != null) }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel(cause = CancellationException("ViewModel onCleared"))
    }
}