package com.kanukim97.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.collection.state.Collection
import com.kanukim97.collection.state.CollectionUiState
import com.kanukim97.domain.repository.CollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val collectionRepository: CollectionRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<CollectionUiState>(CollectionUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            collectionRepository
                .readAllCollections()
                .onStart {
                    _uiState.update { CollectionUiState.Loading }
                }.catch { e ->
                    _uiState.update { CollectionUiState.Failed(e.localizedMessage ?: "") }
                }.map { items ->
                    items
                        .map { item -> Collection(item.id, item.name, item.latLng, item.imageUrl) }
                        .toImmutableList()
                }.collect { collections ->
                    if (collections.isEmpty()) {
                        _uiState.update { CollectionUiState.Empty }
                    } else {
                        _uiState.update { CollectionUiState.Success(collections) }
                    }
                }
        }
    }

}