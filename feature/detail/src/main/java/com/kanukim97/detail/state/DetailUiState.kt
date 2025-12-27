package com.kanukim97.detail.state

import androidx.compose.runtime.Stable

@Stable
sealed interface DetailUiState {
    data object Loading: DetailUiState

    data class Success(val info: DetailUiModel): DetailUiState

    data object Failed: DetailUiState
}

@Stable
data class DetailUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val rating: String,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val isOpened: Boolean
)