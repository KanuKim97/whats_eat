package com.example.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.domain.GetNearBySearchUseCase
import com.example.model.home.BannerItems
import com.example.model.home.GridItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getNearBySearch: GetNearBySearchUseCase
): ViewModel() {
    private val _latLng = MutableLiveData<String>()
    private val latLng: LiveData<String>
        get() = _latLng

    val bannerState: StateFlow<BannerUiState> = bannerState(
        latLng = "37.5519, 126.9918",
        getNearBySearch = getNearBySearch
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = BannerUiState.IsLoading
    )

    val gridItemState: StateFlow<ItemGridUiState> = itemGridState(
        latLng = "37.5519, 126.9918",
        getNearBySearch = getNearBySearch
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
        initialValue = ItemGridUiState.IsLoading
    )

    fun updateLatLng(latLng: String) {
        _latLng.value = latLng
    }
}


private fun bannerState(
    latLng: String,
    getNearBySearch: GetNearBySearchUseCase
): Flow<BannerUiState> {
    val banner = getNearBySearch(latLng)
        .map { resultList ->
            resultList
                .sortedBy { result -> result.rating }
                .slice(indices = 0..resultList.lastIndex/2)
                .map { element ->
                    BannerItems (
                        element.place_id!!,
                        element.name!!,
                        element.photos?.get(0)?.photo_reference!!
                    )
                }
        }

    return banner
        .asResult()
        .map { result ->
            when (result) {
                is Result.IsLoading -> {
                    BannerUiState.IsLoading
                }
                is Result.Success -> {
                    BannerUiState.IsSuccess(result.data)
                }
                is Result.Error -> {
                    BannerUiState.IsFailed
                }
            }
        }
}

private fun itemGridState(
    latLng: String,
    getNearBySearch: GetNearBySearchUseCase
): Flow<ItemGridUiState> {
    val gridItem = getNearBySearch(latLng).map { resultList ->
        resultList.map { element ->
            GridItems(
                element.place_id!!,
                element.name!!,
                element.photos?.get(0)?.photo_reference!!
            )
        }
    }

    return gridItem
        .asResult()
        .map { gridResult ->
            when (gridResult) {
                is Result.IsLoading -> { ItemGridUiState.IsLoading }
                is Result.Success -> { ItemGridUiState.IsSuccess(gridResult.data) }
                is Result.Error -> { ItemGridUiState.IsFailed }
            }
        }
}

sealed interface BannerUiState {
    data class IsSuccess(val banner: List<BannerItems>?): BannerUiState

    data object IsLoading: BannerUiState

    data object IsFailed: BannerUiState
}

sealed interface ItemGridUiState {
    data class IsSuccess(val item: List<GridItems>?): ItemGridUiState

    data object IsLoading: ItemGridUiState

    data object IsFailed: ItemGridUiState
}