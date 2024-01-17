package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Result
import com.example.common.asResult
import com.example.domain.GetNearBySearchUseCase
import com.example.model.home.BannerItems
import com.example.model.home.GridItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearBySearch: GetNearBySearchUseCase
): ViewModel() {

    private val _bannerUiState = MutableStateFlow<BannerUiState>(BannerUiState.IsLoading)
    val bannerUiState: StateFlow<BannerUiState>
        get() = _bannerUiState.asStateFlow()

    private val _itemGridUiState = MutableStateFlow<ItemGridUiState>(ItemGridUiState.IsLoading)
    val itemGridUiState: StateFlow<ItemGridUiState>
        get() = _itemGridUiState.asStateFlow()

    fun getBannerUiState(latLng: String) = viewModelScope.launch {
        bannerState(latLng, getNearBySearch).collect { bannerState ->
            _bannerUiState.value = bannerState
        }
    }

    fun getItemGridState(latLng: String) = viewModelScope.launch {
        itemGridState(latLng, getNearBySearch).collect { gridItemState ->
            _itemGridUiState.value = gridItemState
        }
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