package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.FirebaseDBProducer
import com.example.whats_eat.data.flow.producer.PlaceApiProducer
import com.example.whats_eat.view.dataViewClass.DetailPlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlaceViewModel @Inject constructor(
    private val placeApiProducer: PlaceApiProducer,
    private val fireDBProducer: FirebaseDBProducer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _detailPlaceResult = MutableLiveData<DetailPlace>()
    val detailPlaceResult: LiveData<DetailPlace> get() = _detailPlaceResult


    fun getPlaceDetailData(placeID: String): Job = viewModelScope.launch(ioDispatcher) {
        placeApiProducer.detailedPlace(placeID).collect { result ->
            _detailPlaceResult.postValue(
                DetailPlace(
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

    fun saveUserCollection(place: DetailPlace): Job = viewModelScope.launch(ioDispatcher) {
        fireDBProducer.saveUserCollection(place)
    }

    private fun getPhotoUrl(photoReference: String): String =
        StringBuilder(Constant.PLACE_PHOTO_API_URI)
            .append("?maxwidth=1000")
            .append("&photo_reference=$photoReference")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}