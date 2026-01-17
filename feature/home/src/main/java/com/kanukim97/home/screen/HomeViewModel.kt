package com.kanukim97.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.domain.entities.RestaurantItems
import com.kanukim97.domain.usecases.GetRestaurantItemsUseCase
import com.kanukim97.home.screen.action.HomeUiAction
import com.kanukim97.home.screen.state.HomeUiState
import com.kanukim97.home.screen.state.RestaurantUiItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRestaurantItemsUseCase: GetRestaurantItemsUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _viewModelEvent = Channel<Event>(Channel.BUFFERED)
    val viewModelEvent = _viewModelEvent.receiveAsFlow()

    fun getRestaurantItems(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            getRestaurantItemsUseCase(latitude, longitude)
                .catch {
                    _uiState.update { HomeUiState.Failed }
                }.map {
                    it.map { item -> item.toRestaurantUiItems() }
                }.collect { items ->
                    _uiState.update { HomeUiState.Success(items = items) }
                }
        }
    }

    fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnMarkerClick -> {

            }
            is HomeUiAction.OnRestaurantItemClick -> {

            }
            is HomeUiAction.OnSearch -> {

            }
        }
    }

    sealed interface Event {
        data class OnSearch(val query: String): Event

        data class OnRestaurantItemClick(val id: String): Event

        data class OnMarkerClick(val id: String): Event
    }
}


fun RestaurantItems.toRestaurantUiItems(): RestaurantUiItems {
    return RestaurantUiItems(
        id = id,
        name = name,
        rating = rating,
        reviewsCount = reviewsCount,
        imageUrl = imageUrl,
        distance = distance
    )
}