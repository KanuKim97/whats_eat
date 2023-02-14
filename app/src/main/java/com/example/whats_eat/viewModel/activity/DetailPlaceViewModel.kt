package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.model.detailPlace.ViewPlaceModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class DetailPlaceViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _detailResult = MutableLiveData<String>()

    val detailResult: LiveData<String>
        get() = _detailResult

    fun getDetailPlace(placeID: String) =
        viewModelScope.launch {
            val response = appRepo.getDetailPlace(placeID, BuildConfig.GOOGLE_API_KEY)

            when(response.code()) {
                200 -> { if(response.body()?.status == "OK") { response.body() } }
                else -> {}
            }
        }

    fun storeCollection(
        placeName: String,
        placeAddress: String,
        placeRating: Float,
        placePhotoRef: String
    ) {
        val detailData = ViewPlaceModel(placeName, placeAddress, placeRating, placePhotoRef)
        appRepo.getCollectionPath().push()
            .setValue(detailData)
            .addOnCompleteListener {  }
            .addOnFailureListener {  }
    }

    fun getPhotoUrl(photoRef: String) =
        StringBuilder(Constant.IPlacePhotoAPIUri)
            .append("?maxwidth=${Constant.photoMaxWidth}")
            .append("&photo_reference=$photoRef")
            .append("&key=${BuildConfig.GOOGLE_API_KEY}")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}