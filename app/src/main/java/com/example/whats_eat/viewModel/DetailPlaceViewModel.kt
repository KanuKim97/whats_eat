package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.collection.Collection
import com.example.domain.GetDetailPlaceItemUseCase
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.common.Constant
import com.example.whats_eat.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlaceViewModel @Inject constructor(
    private val getDetailPlaceItemUseCase: GetDetailPlaceItemUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _detailPlaceResult = MutableLiveData<Collection>()
    val detailPlaceResult: LiveData<Collection> get() = _detailPlaceResult

    fun getDetailPlaceItem(placeId: String): Job = viewModelScope.launch(ioDispatcher) {
        getDetailPlaceItemUseCase(placeId).collect { result ->
            if (result != null) {
                _detailPlaceResult.postValue(
                    Collection(
                        name = result.name,
                        formattedAddress = result.formatted_address,
                        isOpenNow = result.openingHours?.open_now,
                        rating = result.rating,
                        lat = result.geometry?.location?.lat,
                        lng = result.geometry?.location?.lng,
                        photoRef = getPhotoUrl(result.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
        }
    }

    private fun getPhotoUrl(photoReference: String): String =
        StringBuilder(Constant.PLACE_PHOTO_API_URI)
            .append("?maxwidth=1000")
            .append("&photo_reference=$photoReference")
            .append("&key=${BuildConfig.MAPS_API_KEY}")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}