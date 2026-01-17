package com.kanukim97.home.screen.state

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface HomeUiState {
    data object Loading: HomeUiState

    data class Success(
        val query: String = "",
        val items: List<RestaurantUiItems> = emptyList()
    ): HomeUiState

    data object Failed: HomeUiState
}


@Immutable
data class RestaurantUiItems(
    val id: String,
    val name: String,
    val rating: String?,
    val reviewsCount: Int?,
    val imageUrl: String?,
    val distance: Double?
)