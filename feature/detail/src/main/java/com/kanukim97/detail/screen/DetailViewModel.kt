package com.kanukim97.detail.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.detail.screen.action.DetailUiAction
import com.kanukim97.detail.navigation.PlaceIdArgs
import com.kanukim97.detail.screen.state.DetailUiModel
import com.kanukim97.detail.screen.state.DetailUiState
import com.kanukim97.detail.screen.state.Review
import com.kanukim97.domain.repository.CollectionRepository
import com.kanukim97.domain.usecases.GetPlaceDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase,
    private val collectionRepository: CollectionRepository
): ViewModel() {
    private val args by lazy { PlaceIdArgs(savedStateHandle) }

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _viewModelEvent = Channel<Event>(Channel.BUFFERED)
    val viewModelEvent: Flow<Event> = _viewModelEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            getPlaceDetailUseCase(args.id)
                .catch {
                    _uiState.update { DetailUiState.Failed }
                }.map { info ->
                    DetailUiModel(
                        id = info?.id ?: "",
                        name = info?.name ?: "",
                        address = info?.address ?: "",
                        imageUrl = info?.imageUrl ?: "",
                        latitude = info?.latitude ?: 0.0,
                        longitude = info?.longitude ?: 0.0,
                        rating = info?.rating ?: "",
                        phoneNumber = info?.phoneNumber ?: "",
                        isOpened = info?.isOpenNow == true,
                        url = info?.url ?: "",
                        reviewsCount = info?.reviewsCount,
                        reviews = info?.review?.map { review ->
                            Review(
                                userImageUrl = review.profilePhotoUrl ?: "",
                                authorName = review.authorName,
                                rating = review.rating,
                                text = review.content
                            )
                        } ?: emptyList()
                    )
                }.collect { data ->
                    _uiState.update { DetailUiState.Success(data) }
                }
        }
    }

    fun handleAction(action: DetailUiAction) {
        when (action) {
            DetailUiAction.OnBackBtnClick -> {
                _viewModelEvent.trySend(Event.NavigateBack)
            }
            DetailUiAction.OnShareBtnClick -> {
                if (_uiState.value !is DetailUiState.Success) return

                val shareText = (_uiState.value as? DetailUiState.Success)?.info?.url
                if (shareText.isNullOrBlank()) return

                _viewModelEvent.trySend(Event.ShowShareIntent(shareText = shareText))
            }
            DetailUiAction.OnAddCollection -> {
                if (_uiState.value !is DetailUiState.Success) return

                val data = (_uiState.value as DetailUiState.Success).info

                viewModelScope.launch {
                    runCatching {
                        collectionRepository.saveCollection(
                            id = data.id,
                            name = data.name,
                            latLng = "${data.latitude},${data.longitude}",
                            imageUrl = data.imageUrl
                        )
                    }.onSuccess {

                    }.onFailure {

                    }
                }
            }
            DetailUiAction.OnDialIconBtnClick -> {
                if (_uiState.value !is DetailUiState.Success) return
                val phoneNumber = (_uiState.value as? DetailUiState.Success)?.info?.phoneNumber ?: return

                _viewModelEvent.trySend(Event.ShowCallIntent(phoneNumber))
            }
            DetailUiAction.OnGetDirectionsBtnClick -> {
                if (_uiState.value !is DetailUiState.Success) return

                val name = (_uiState.value as? DetailUiState.Success)?.info?.name ?: return
                val latitude = (_uiState.value as? DetailUiState.Success)?.info?.latitude ?: return
                val longitude = (_uiState.value as? DetailUiState.Success)?.info?.longitude ?: return

                if (latitude == 0.0 || longitude == 0.0) return

                _viewModelEvent.trySend(
                    Event.ShowMapsIntent(latLng = "${latitude},${longitude}", name = name)
                )
            }
            DetailUiAction.OnSeeAllReviewBtnClick -> {

            }
            DetailUiAction.OnSeeFullMenuClick -> {

            }
        }
    }

    sealed interface Event {
        data object NavigateBack: Event

        data class ShowCallIntent(val phoneNumber: String): Event

        data class ShowShareIntent(val shareText: String): Event

        data class ShowMapsIntent(val latLng: String, val name: String): Event

        data class ShowDialog(val msg: String): Event

        data class ShowSnackBar(val msg: String): Event

        data class ShowToast(val msg: String): Event
    }
}