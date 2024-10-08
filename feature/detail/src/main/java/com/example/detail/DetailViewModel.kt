package com.example.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detail.navigation.PlaceIdArgs
import com.example.domain.network.GetPlaceDetailUseCase
import com.example.domain.database.SaveCollectionUseCase
import com.example.model.domain.CollectionModel
import com.example.model.domain.DetailedModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
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
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val saveUserCollectionUseCase: SaveCollectionUseCase
): ViewModel() {
    private val _saveCollectionState = MutableStateFlow<SaveCollectionState>(SaveCollectionState.Init)
    val saveCollectionState: StateFlow<SaveCollectionState> = _saveCollectionState

    val detailUiState = detailState(
        placeID = PlaceIdArgs(savedStateHandle).placeID,
        getPlaceDetailUseCase = getPlaceDetailUseCase
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = DetailUiState.IsLoading
    )

    fun saveCollection(content: CollectionModel): Job = viewModelScope.launch {
        saveCollectionState(
            collection = content,
            saveUserCollectionUseCase = saveUserCollectionUseCase
        ).collect { state ->
            _saveCollectionState.value = state
        }
    }

    private fun saveCollectionState(
        collection: CollectionModel,
        saveUserCollectionUseCase: SaveCollectionUseCase
    ): Flow<SaveCollectionState> {
        return saveUserCollectionUseCase(collection)
            .onStart { _saveCollectionState.value = SaveCollectionState.IsLoading }
            .catch { exception ->
                _saveCollectionState.value = SaveCollectionState.IsFailed(exception.message)
            }
            .map { SaveCollectionState.IsSuccess }
    }
}

private fun detailState(
    placeID: String,
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): Flow<DetailUiState> {
    return getPlaceDetailUseCase(placeID)
        .onStart { DetailUiState.IsLoading }
        .catch { exception ->
            exception.printStackTrace()

            DetailUiState.IsFailed
        }
        .map<DetailedModel, DetailUiState> { result -> DetailUiState.IsSuccess(result) }
}

sealed interface DetailUiState {
    data object IsLoading: DetailUiState

    data class IsSuccess(val info: DetailedModel): DetailUiState

    data object IsFailed: DetailUiState
}

sealed interface SaveCollectionState {
    data object Init: SaveCollectionState

    data object IsLoading: SaveCollectionState

    data object IsSuccess: SaveCollectionState

    data class IsFailed(val message: String? = null): SaveCollectionState
}