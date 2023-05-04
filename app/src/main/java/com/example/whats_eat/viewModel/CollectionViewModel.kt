package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.FirebaseDBProducer
import com.example.whats_eat.view.dataViewClass.DetailPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dataBaseProducer: FirebaseDBProducer
): ViewModel() {
    val collectionFlow: Flow<ArrayList<DetailPlace>> get() = dataBaseProducer.userCollection

    init {
        viewModelScope.launch(ioDispatcher) {
            dataBaseProducer.loadUserCollection()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}