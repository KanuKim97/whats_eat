package com.kanukim97.detail.state

import androidx.compose.runtime.Stable
import com.kanukim97.domain.model.DetailedDomainModel

@Stable
sealed interface DetailUiState {
    data object Loading: DetailUiState

    data class Success(val info: DetailedDomainModel): DetailUiState

    data object Failed: DetailUiState
}

@Stable
sealed interface SaveCollectionState {
    data object Init: SaveCollectionState

    data object Loading: SaveCollectionState

    data object Success: SaveCollectionState

    data class Failed(val message: String? = null): SaveCollectionState
}