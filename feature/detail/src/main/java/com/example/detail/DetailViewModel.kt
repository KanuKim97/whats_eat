package com.example.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DatabaseRepository
import com.example.detail.navigation.PlaceIdArgs
import com.example.domain.network.GetPlaceDetailUseCase
import com.example.domain.model.PlaceDetailItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val dbRepository: DatabaseRepository
): ViewModel() {
    private var _saveCollectionState = MutableStateFlow<SaveCollectionState>(SaveCollectionState.Init)
    val saveCollectionState: StateFlow<SaveCollectionState> = _saveCollectionState

    val detailUiState = detailState(
        placeID = PlaceIdArgs(savedStateHandle).placeID,
        getPlaceDetailUseCase = getPlaceDetailUseCase
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = DetailUiState.IsLoading
    )

    fun savePlaceInfo(
        placeId: String,
        placeName: String,
        placeImgUrl: String,
        placeLatLng: String
    ) = viewModelScope.launch {
        saveCollectionState(
            placeId = placeId,
            placeName = placeName,
            placeImgUrl = placeImgUrl,
            placeLatLng = placeLatLng,
            databaseRepository = dbRepository
        ).collectLatest { result -> _saveCollectionState.value = result }
    }

    private fun saveCollectionState(
        placeId: String,
        placeName: String,
        placeImgUrl: String,
        placeLatLng: String,
        databaseRepository: DatabaseRepository
    ): Flow<SaveCollectionState> {
        return databaseRepository
            .saveUserCollection(
                placeId = placeId,
                placeName = placeName,
                placeImgUrl = placeImgUrl,
                placeLatLng = placeLatLng
            )
            .onStart { _saveCollectionState.value = SaveCollectionState.IsLoading }
            .catch { exception ->
                _saveCollectionState.value = SaveCollectionState.IsFailed(exception.message)
            }
            .map { SaveCollectionState.IsSuccess }
            .onCompletion { _saveCollectionState.value = SaveCollectionState.Init }
    }

}

private fun detailState(
    placeID: String,
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): Flow<DetailUiState> {
    return getPlaceDetailUseCase(placeID)
        .onStart { DetailUiState.IsLoading }
        .catch { _ -> DetailUiState.IsFailed }
        .map<PlaceDetailItemModel, DetailUiState> { result -> DetailUiState.IsSuccess(result) }
}

sealed interface DetailUiState {
    data object IsLoading: DetailUiState

    data class IsSuccess(val info: PlaceDetailItemModel): DetailUiState

    data object IsFailed: DetailUiState
}

sealed interface SaveCollectionState {
    data object Init: SaveCollectionState

    data object IsLoading: SaveCollectionState

    data object IsSuccess: SaveCollectionState

    data class IsFailed(val message: String? = null): SaveCollectionState
}