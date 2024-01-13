package com.example.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.domain.GetPlaceDetailUseCase
import com.example.model.response.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): ViewModel() {
    val detailState = detailState(
        placeID = "",
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
        .asResult()
        .map { result ->
            when (result) {
                is Result.IsLoading -> { DetailUiState.IsLoading }
                is Result.Success -> { DetailUiState.IsSuccess(result.data) }
                is Result.Error -> { DetailUiState.IsFailed }
            }
        }
}

sealed interface DetailUiState {
    data object IsLoading: DetailUiState

    data class IsSuccess(val info: Results?): DetailUiState

    data object IsFailed: DetailUiState
}