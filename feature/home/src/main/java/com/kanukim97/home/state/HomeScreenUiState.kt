package com.kanukim97.home.state

import androidx.compose.runtime.Stable
import com.kanukim97.domain.model.NearByPlaceItemModel

@Stable
sealed interface BannerUiState {
    data object Init: BannerUiState

    data object Loading: BannerUiState

    data class Success(val banner: List<NearByPlaceItemModel>?): BannerUiState

    data class Failed(val message: String = ""): BannerUiState

    data object Empty: BannerUiState
}

@Stable
sealed interface ItemGridUiState {
    data object Init: ItemGridUiState

    data object Loading: ItemGridUiState

    data class Success(val item: List<NearByPlaceItemModel>?): ItemGridUiState

    data object Failed: ItemGridUiState

    data object Empty: ItemGridUiState
}