package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.auth.DeleteAccountUseCase
import com.example.domain.usecase.auth.LogOutAccountUseCase
import com.example.domain.usecase.database.LoadAllCollectionCountUseCase
import com.example.domain.usecase.database.LoadProfileUseCase
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logOutAccountUseCase: LogOutAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val loadProfileUseCase: LoadProfileUseCase,
    private val loadCollectionCntUseCase: LoadAllCollectionCountUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _isAccountDeleteSuccess = MutableLiveData<Boolean>()
    private val _isSignOutSuccess = MutableLiveData<Boolean>()
    val isAccountDeleteSuccess: LiveData<Boolean> get() = _isAccountDeleteSuccess
    val isSignOutSuccess: LiveData<Boolean> get() = _isSignOutSuccess

    val userFlow get() = loadProfileUseCase.userProfile
    val collectionFlow get() = loadCollectionCntUseCase.collectionItemCnt

    init {
        viewModelScope.launch(ioDispatcher) {
            launch { loadProfileUseCase.loadUserProfile() }.join()
            launch { loadCollectionCntUseCase.loadAllUserCollectionCount() }
        }
    }

    fun signOutUserAccount(): Job = viewModelScope.launch(ioDispatcher) {
        logOutAccountUseCase.logOutUserAccount().collect { result ->
            when(result) {
                true -> _isSignOutSuccess.postValue(true)
                false -> _isSignOutSuccess.postValue(false)
            }
        }
    }

    fun deleteUserAccount(): Job = viewModelScope.launch(ioDispatcher) {
        deleteAccountUseCase.deleteUserAccount().collect { result ->
            when {
                result.isSuccess -> _isAccountDeleteSuccess.postValue(true)
                result.isFailure -> _isAccountDeleteSuccess.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}