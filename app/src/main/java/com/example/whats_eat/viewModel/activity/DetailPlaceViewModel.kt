package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.data.di.repository.FireBaseRTDBRepository
import com.example.whats_eat.data.di.repository.PlaceApiRepository
import com.example.whats_eat.data.remote.model.dataClassView.ViewPlaceModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailPlaceViewModel @Inject constructor(
    private val rtDBRepo: FireBaseRTDBRepository,
    private val placeApiRepo: PlaceApiRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _detailedResult = MutableLiveData<String>()
    private val _pushDBResult = MutableLiveData<String>()

    val detailedResult: LiveData<String> get() = _detailedResult
    val pushDBResult: LiveData<String> get() = _pushDBResult

    /* TODO : Is it necessary? */
    fun getDetailedPlaceResult(place_ID: String) = viewModelScope.launch(ioDispatcher) {
        val response = placeApiRepo.detailedPlace(place_ID)

        if (response.isSuccessful && response.body()?.result != null) {
            /* TODO : Is it necessary? */
        }
    }

    fun storeResultData(
        place_ID: String,
        placeName: String,
        placeAddress: String,
        placeRating: Float,
        placePhotoRef: String
    ) = viewModelScope.launch(ioDispatcher) {
        val detailedData = ViewPlaceModel(placeName, placeAddress, placeRating, placePhotoRef)

        rtDBRepo.getUserCollectionDBRef().child(place_ID).push().setValue(detailedData)
            .addOnCompleteListener {
                if (it.isSuccessful) { _pushDBResult.value = "Success" }
                else { it.exception?.printStackTrace() }
            }.addOnFailureListener { it.printStackTrace() }
    }

    /* TODO : Is it necessary? */
    fun getPhotoUrl(photoRef: String) =
        StringBuilder(Constant.PLACE_PHOTO_API_URL)
            .append("?maxwidth=${Constant.PHOTO_MAX_WIDTH}")
            .append("&photo_reference=$photoRef")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}