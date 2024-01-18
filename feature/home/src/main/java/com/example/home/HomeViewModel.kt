package com.example.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.GetGridItemUseCase
import com.example.domain.GetMainBannerUseCase
import com.example.model.home.BannerItems
import com.example.model.home.GridItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private var _bannerUiState = MutableStateFlow<BannerUiState>(BannerUiState.IsLoading)
    val bannerUiState: StateFlow<BannerUiState>
        get() =  _bannerUiState.asStateFlow()


    private val _itemGridUiState = MutableStateFlow<ItemGridUiState>(ItemGridUiState.IsLoading)
    val itemGridUiState: StateFlow<ItemGridUiState>
        get() = _itemGridUiState.asStateFlow()

    fun getBannerUiState(latLng: String) = viewModelScope.launch {
        bannerUiState(latLng, getMainBannerUseCase).collect { bannerUiState ->
            _bannerUiState.value = bannerUiState
        }
    }

    fun getItemGridUiState(latLng: String) = viewModelScope.launch {
        itemGridUiState(latLng, getGridItemUseCase).collect { itemGridUiState ->
            _itemGridUiState.value = itemGridUiState
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

private fun bannerUiState(
    latLng: String,
    getMainBannerUseCase: GetMainBannerUseCase
): Flow<BannerUiState> {
    return getMainBannerUseCase(latLng)
        .onStart { BannerUiState.IsLoading }
        .catch { BannerUiState.IsFailed }
        .map<List<BannerItems>, BannerUiState> { bannerList ->
            BannerUiState.IsSuccess(bannerList)
        }
}

private fun itemGridUiState(
    latLng: String,
    getGridItemUseCase: GetGridItemUseCase
): Flow<ItemGridUiState> {
    return getGridItemUseCase(latLng)
        .onStart { ItemGridUiState.IsLoading }
        .catch { ItemGridUiState.IsFailed }
        .map<List<GridItems>, ItemGridUiState> { itemGridList ->
            ItemGridUiState.IsSuccess(itemGridList)
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