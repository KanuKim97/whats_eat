package com.example.collection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.repository.DatabaseRepository
import com.example.domain.model.CollectionDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    databaseRepository: DatabaseRepository
): ViewModel() {
    val readAllCollectionUiState: StateFlow<ReadAllCollectionUiState> =
        readAllCollectionState(databaseRepository)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = ReadAllCollectionUiState.IsLoading
            )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}

private fun readAllCollectionState(
    databaseRepository: DatabaseRepository
): Flow<ReadAllCollectionUiState> {
    return databaseRepository.readAllCollectionEntities()
        .onStart { ReadAllCollectionUiState.IsLoading }
        .catch { exception ->
            ReadAllCollectionUiState.IsFailed(exception.message.toString())
        }.map { collectionList ->
            val collections = collectionList.map { entity ->
                CollectionDomainModel(
                    id = entity.id,
                    name = entity.name,
                    latLng = entity.latLng,
                    imgUrl = entity.imgUrl
                )
            }

            ReadAllCollectionUiState.IsSuccess(collections)
        }
}

sealed interface ReadAllCollectionUiState {
    data object IsLoading: ReadAllCollectionUiState

    data class IsSuccess(val data: List<CollectionDomainModel>): ReadAllCollectionUiState

    data class IsFailed(val msg: String): ReadAllCollectionUiState
}