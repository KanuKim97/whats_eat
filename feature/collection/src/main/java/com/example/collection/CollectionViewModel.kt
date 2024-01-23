package com.example.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.DeleteCollectionUseCase
import com.example.domain.ReadAllCollectionUseCase
import com.example.model.feature.CollectionModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    readAllCollectionUseCase: ReadAllCollectionUseCase,
    private val deleteCollectionUseCase: DeleteCollectionUseCase
): ViewModel() {
    private val _deleteCollectionState =
        MutableStateFlow<DeleteCollectionUiState>(DeleteCollectionUiState.IsLoading)
    val deleteCollectionState: StateFlow<DeleteCollectionUiState>
        get() = _deleteCollectionState


    val readAllCollectionUiState: StateFlow<ReadAllCollectionUiState> =
        readAllCollectionState(readAllCollectionUseCase)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ReadAllCollectionUiState.IsLoading
            )

    fun deleteCollection(collection: CollectionModel): Job = viewModelScope.launch {
        deleteCollectionState(
            collection = collection,
            deleteCollectionUseCase = deleteCollectionUseCase
        ).collect { state -> _deleteCollectionState.value = state }
    }

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
        .catch { ReadAllCollectionUiState.IsFailed }
        .map<List<CollectionModel>, ReadAllCollectionUiState> { collectionList ->
            ReadAllCollectionUiState.IsSuccess(collectionList)
        }
}

private fun deleteCollectionState(
    collection: CollectionModel,
    deleteCollectionUseCase: DeleteCollectionUseCase
): Flow<DeleteCollectionUiState> {
    return deleteCollectionUseCase(collection)
        .onStart { DeleteCollectionUiState.IsLoading }
        .catch { DeleteCollectionUiState.IsFailed }
        .map<Result<Unit>, DeleteCollectionUiState> { result ->
            DeleteCollectionUiState.IsSuccess(result)
        }
}

sealed interface ReadAllCollectionUiState {
    data object IsLoading: ReadAllCollectionUiState

    data class IsSuccess(val data: List<CollectionModel>): ReadAllCollectionUiState

    data object IsFailed: ReadAllCollectionUiState
}

sealed interface DeleteCollectionUiState {
    data object IsLoading: DeleteCollectionUiState

    data class IsSuccess(val data: Result<Unit>): DeleteCollectionUiState

    data object IsFailed: DeleteCollectionUiState
}