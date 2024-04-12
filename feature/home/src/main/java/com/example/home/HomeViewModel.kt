package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BuildConfig
import com.example.domain.network.GetGridItemUseCase
import com.example.domain.network.GetMainBannerUseCase
import com.example.domain.entity.BannerItemsModel
import com.example.domain.entity.GridItemsModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMainBannerUseCase: GetMainBannerUseCase,
    private val getGridItemUseCase: GetGridItemUseCase
): ViewModel() {
    private var _bannerUiState = MutableStateFlow<BannerUiState>(BannerUiState.Init)
    val bannerUiState: StateFlow<BannerUiState> = _bannerUiState

    private val _itemGridUiState = MutableStateFlow<ItemGridUiState>(ItemGridUiState.Init)
    val itemGridUiState: StateFlow<ItemGridUiState> = _itemGridUiState

    fun getBannerUiState(latLng: String) = viewModelScope.launch {
        bannerUiState(latLng, getMainBannerUseCase).collect { bannerState ->
            _bannerUiState.value = bannerState
        }
    }

    fun getItemGridUiState(latLng: String) = viewModelScope.launch {
        itemGridUiState(latLng, getGridItemUseCase).collect { itemGridUiState ->
            _itemGridUiState.value = itemGridUiState
        }
    }

    private fun bannerUiState(
        latLng: String,
        getMainBannerUseCase: GetMainBannerUseCase
    ): Flow<BannerUiState> {
        return getMainBannerUseCase(latLng)
            .onStart { _bannerUiState.value = BannerUiState.IsLoading }
            .catch { exception ->
                _bannerUiState.value = BannerUiState.IsFailed(exception.message.toString())
            }.map { result ->
                val bannerItemsModel = result.map { item ->
                    BannerItemsModel(
                        placeID = item.place_id,
                        name = item.name,
                        photoRef = makePhotoRef(item.photos[0].photo_reference)
                    )
                }

                BannerUiState.IsSuccess(bannerItemsModel)
            }
    }

    private fun itemGridUiState(
        latLng: String,
        getGridItemUseCase: GetGridItemUseCase
    ): Flow<ItemGridUiState> {
        return getGridItemUseCase(latLng)
            .onStart { _itemGridUiState.value = ItemGridUiState.IsLoading }
            .catch { _itemGridUiState.value = ItemGridUiState.IsFailed }
            .map { result ->
                val itemGridList = result.map { item ->
                    GridItemsModel(
                        placeID = item.place_id,
                        name = item.name,
                        photoRef = if (item.photos != null) {
                            makePhotoRef(item.photos[0].photo_reference)
                        } else {
                            ""
                        }
                    )
                }

                ItemGridUiState.IsSuccess(itemGridList)
            }
    }

    private fun makePhotoRef(photoRef: String): String {
        return StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=1000")
            .append("&photo_reference=${photoRef}")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}


sealed interface BannerUiState {
    data object Init: BannerUiState

    data object IsLoading: BannerUiState

    data class IsSuccess(val banner: List<BannerItemsModel>?): BannerUiState

    data class IsFailed(val message: String = ""): BannerUiState
}

sealed interface ItemGridUiState {
    data class IsSuccess(val item: List<GridItemsModel>?): ItemGridUiState

    data object Init: ItemGridUiState

    data object IsLoading: ItemGridUiState

    data object IsFailed: ItemGridUiState
}