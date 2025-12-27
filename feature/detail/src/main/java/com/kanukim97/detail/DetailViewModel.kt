package com.kanukim97.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kanukim97.detail.action.DetailUiAction
import com.kanukim97.detail.navigation.PlaceIdArgs
import com.kanukim97.detail.state.DetailUiModel
import com.kanukim97.detail.state.DetailUiState
import com.kanukim97.domain.usecases.GetPlaceDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPlaceDetailUseCase: GetPlaceDetailUseCase
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
                        isOpened = info?.isOpenNow == true
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
            DetailUiAction.OnCallBtnClick -> {

            }
            DetailUiAction.OnGetDirectionsBtnClick -> {

            }
            is DetailUiAction.OnLikeBtnClick -> {

            }
            DetailUiAction.OnSeeAllReviewBtnClick -> {

            }
            DetailUiAction.OnSeeFullMenuClick -> {

            }
            DetailUiAction.OnShareBtnClick -> {

            }
        }
    }

    sealed interface Event {
        data object NavigateBack: Event

        data class ShowDialog(val msg: String): Event

        data class ShowSnackBar(val msg: String): Event

        data class ShowToast(val msg: String): Event
    }
}