package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.FirebaseAuthProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoViewModel @Inject constructor(
    private val authProducer: FirebaseAuthProducer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _readFireAuth = MutableLiveData<Boolean>()
    val readFireAuth: LiveData<Boolean> get() = _readFireAuth

    init { checkUserCurrentSession() }

    private fun checkUserCurrentSession(): Job = viewModelScope.launch(ioDispatcher) {
        authProducer.getCurrentUser().collect { userSession ->
            _readFireAuth.postValue(userSession != null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
