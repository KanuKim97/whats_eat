package com.kanukim97.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.data.repository.DatabaseRepository
import com.kanukim97.domain.model.DetailedDomainModel
import com.kanukim97.domain.network.GetPlaceDetailUseCase
import com.kanukim97.detail.state.DetailUiState
import com.kanukim97.detail.state.SaveCollectionState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val dbRepository: DatabaseRepository
): ViewModel() {
    private var _saveCollectionState = MutableStateFlow<SaveCollectionState>(SaveCollectionState.Init)
    val saveCollectionState: StateFlow<SaveCollectionState> = _saveCollectionState

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    fun getDetailUiState(placeId: String) = viewModelScope.launch {
        detailState(
            placeId = placeId,
            getPlaceDetailUseCase = getPlaceDetailUseCase
        ).collectLatest { state -> _detailUiState.value = state }
    }

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
            .onStart { _saveCollectionState.value = SaveCollectionState.Loading }
            .catch { exception ->
                _saveCollectionState.value = SaveCollectionState.Failed(exception.message)
            }
            .map { SaveCollectionState.Success }
            .onCompletion { _saveCollectionState.value = SaveCollectionState.Init }
    }

}

private fun detailState(
    placeId: String,
    getPlaceDetailUseCase: GetPlaceDetailUseCase
): Flow<DetailUiState> {
    return getPlaceDetailUseCase(placeId)
        .onStart { DetailUiState.Loading }
        .catch { _ -> DetailUiState.Failed }
        .map<DetailedDomainModel, DetailUiState> { result -> DetailUiState.Success(result) }
}