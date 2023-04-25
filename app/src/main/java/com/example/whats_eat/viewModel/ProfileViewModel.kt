package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.FirebaseAuthProducer
import com.example.whats_eat.data.flow.producer.FirebaseDBProducer
import com.example.whats_eat.view.dataViewClass.ProfileClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authProducer: FirebaseAuthProducer,
    private val dataBaseProducer: FirebaseDBProducer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _isAccountDeleteSuccess = MutableLiveData<Boolean>()
    val isAccountDeleteSuccess: LiveData<Boolean> get() = _isAccountDeleteSuccess
    val userFlow: Flow<ProfileClass> get() = dataBaseProducer.userProfile
    val collectionFlow: Flow<String> get() = dataBaseProducer.userCollectionCount

    init {
        viewModelScope.launch(ioDispatcher) {
            dataBaseProducer.loadUserProfile()
            dataBaseProducer.loadUserCollectionCount()
        }
    }

    fun deleteUserAccount(): Job = viewModelScope.launch(ioDispatcher) {
        authProducer.deleteUserAccount().collect { result ->
            when {
                result.isSuccess -> _isAccountDeleteSuccess.postValue(true)
                result.isFailure -> _isAccountDeleteSuccess.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        dataBaseProducer.stopEventListening()
    }
}