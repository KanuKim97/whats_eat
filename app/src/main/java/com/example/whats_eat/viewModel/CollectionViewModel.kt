package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.database.LoadAllCollectionCountUseCase
import com.example.domain.usecase.database.LoadAllCollectionUseCase
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.presenter.ViewModelItems.DetailPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val loadCollectionUseCase: LoadAllCollectionUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    //val collectionFlow: Flow<ArrayList<DetailPlace>> get() = dataBaseProducer.userCollection

    init { viewModelScope.launch(ioDispatcher) { loadCollectionUseCase.loadAllUserCollection() } }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}