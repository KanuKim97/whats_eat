package com.example.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.detail.navigation.PlaceIdArgs
import com.example.domain.GetPlaceDetailUseCase
import com.example.model.response.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): ViewModel() {
    val detailUiState = detailState(
        placeID = PlaceIdArgs(savedStateHandle).placeID,
        getPlaceDetailUseCase = getPlaceDetailUseCase
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = DetailUiState.IsLoading
    )
}

private fun detailState(
    placeID: String,
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): Flow<DetailUiState> {
    return getPlaceDetailUseCase(placeID)
        .onStart { DetailUiState.IsLoading }
        .catch { DetailUiState.IsFailed }
        .map<Results?, DetailUiState> { result ->
            DetailUiState.IsSuccess(result)
        }
}

sealed interface DetailUiState {
    data object IsLoading: DetailUiState

    data class IsSuccess(val info: Results?): DetailUiState

    data object IsFailed: DetailUiState
}