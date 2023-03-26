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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    /* TODO : Need to Refactor this ViewModel */
    private val _pushDBResult = MutableLiveData<String>()
    val pushDBResult: LiveData<String> get() = _pushDBResult

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