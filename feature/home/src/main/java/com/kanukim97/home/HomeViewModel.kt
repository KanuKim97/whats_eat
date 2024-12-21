package com.kanukim97.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.domain.model.NearByPlaceItemModel
import com.kanukim97.domain.network.GetGridItemUseCase
import com.kanukim97.domain.network.GetMainBannerUseCase
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
            .onStart { BannerUiState.Loading }
            .catch { BannerUiState.Failed(it.message.toString()) }
            .map { result ->
                if (result.isEmpty()) BannerUiState.Empty

                BannerUiState.Success(result)
            }
    }

    private fun itemGridUiState(
        latLng: String,
        getGridItemUseCase: GetGridItemUseCase
    ): Flow<ItemGridUiState> {
        return getGridItemUseCase(latLng)
            .onStart { ItemGridUiState.Loading }
            .catch { ItemGridUiState.Failed }
            .map { result ->
                if (result.isEmpty()) ItemGridUiState.Empty

                ItemGridUiState.Success(result)
            }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}