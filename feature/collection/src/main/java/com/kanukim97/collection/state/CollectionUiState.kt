package com.kanukim97.collection.state

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList

@Stable
sealed interface CollectionUiState {
    data object Loading: CollectionUiState

    data class Success(val items: ImmutableList<Collection>): CollectionUiState

    data class Failed(val msg: String): CollectionUiState

    data object Empty: CollectionUiState
}

@Stable
data class Collection(
    val id: String,
    val name: String,
    val latLng: String,
    val imageUrl: String = ""
)
