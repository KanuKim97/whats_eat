package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.CreateAccountUseCase
import com.example.domain.usecase.database.SaveProfileUseCase
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val createAccountUseCase: CreateAccountUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _isCreateSuccess = MutableLiveData<Boolean>()
    val isCreateSuccess: LiveData<Boolean> get() = _isCreateSuccess

    fun createAccount(
        userEmail: String,
        userPassword: String,
        userFullName: String,
        userNickName: String
    ): Job = viewModelScope.launch(ioDispatcher) {
        createAccountUseCase(userEmail, userPassword).collect {
            when {
                it.isSuccess -> {
                    saveProfileUseCase.saveUserProfile(userEmail, userNickName, userFullName)
                    _isCreateSuccess.postValue(true)
                }
                it.isFailure -> {
                    _isCreateSuccess.postValue(false)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}