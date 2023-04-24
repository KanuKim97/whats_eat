package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.di.repository.FireBaseDBRepository
import com.example.whats_eat.data.flow.producer.FirebaseAuthProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val rtDBRepo: FireBaseDBRepository,
    private val authProvider: FirebaseAuthProducer,
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
        authProvider.createUserAccount(userEmail, userPassword).collect {
            when {
                it.isSuccess -> {
                    _isCreateSuccess.postValue(true)
                    setUserInfoInDB(userEmail, userNickName, userFullName)
                }
                it.isFailure -> {
                    _isCreateSuccess.postValue(false)
                }
            }
        }
    }


    private fun setUserInfoInDB(userEmail: String, userNickName: String, userFullName: String) {
        val currentUserDBRef = rtDBRepo.getUserDBRef()

        currentUserDBRef.child("userEmail").setValue(userEmail)
        currentUserDBRef.child("userNickName").setValue(userNickName)
        currentUserDBRef.child("userFullName").setValue(userFullName)
    }
}