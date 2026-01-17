package com.kanukim97.home.screen.action

sealed interface HomeUiAction {
    data class OnSearch(val query: String): HomeUiAction

    data class OnRestaurantItemClick(val id: String): HomeUiAction

    data class OnMarkerClick(val id: String): HomeUiAction
}