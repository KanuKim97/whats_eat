package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.CurrentUserUseCase
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoViewModel @Inject constructor(
    private val currentUserUseCase: CurrentUserUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _readFireAuth = MutableLiveData<Boolean>()
    val readFireAuth: LiveData<Boolean> get() = _readFireAuth

    init { checkCurrentUser() }

    private fun checkCurrentUser() = viewModelScope.launch(ioDispatcher) {
        currentUserUseCase.getCurrentUser().collect { userSession ->
            _readFireAuth.postValue(userSession != null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
