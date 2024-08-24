package com.example.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.database.ReadAllCollectionUseCase
import com.example.model.domain.CollectionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    readAllCollectionUseCase: ReadAllCollectionUseCase,
): ViewModel() {
    val readAllCollectionUiState: StateFlow<ReadAllCollectionUiState> =
        readAllCollectionState(readAllCollectionUseCase)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ReadAllCollectionUiState.IsLoading
            )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

private fun readAllCollectionState(
    readAllCollectionUseCase: ReadAllCollectionUseCase
): Flow<ReadAllCollectionUiState> {
    return readAllCollectionUseCase()
        .onStart { ReadAllCollectionUiState.IsLoading }
        .catch { exception ->
            ReadAllCollectionUiState.IsFailed(exception.message.toString())
        }
        .map<List<CollectionModel>, ReadAllCollectionUiState> { collectionList ->
            ReadAllCollectionUiState.IsSuccess(collectionList)
        }
}


sealed interface ReadAllCollectionUiState {
    data object IsLoading: ReadAllCollectionUiState

    data class IsSuccess(val data: List<CollectionModel>): ReadAllCollectionUiState

    data class IsFailed(val msg: String): ReadAllCollectionUiState
}