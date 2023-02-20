package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.example.whats_eat.data.di.repository.PlaceApiRepository
import com.example.whats_eat.data.remote.model.detailPlace.ViewPlaceModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailPlaceViewModel @Inject constructor(
    private val firebaseRepo: FirebaseRepository,
    private val placeApiRepo: PlaceApiRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
    ): ViewModel() {
    private val _detailedResult = MutableLiveData<String>()
    private val _pushDBResult = MutableLiveData<String>()
    val detailedResult: LiveData<String> get() = _detailedResult
    val pushDBResult: LiveData<String> get() = _pushDBResult

    fun getDetailedPlaceResult(place_ID: String) = viewModelScope.launch(ioDispatcher) {
        val response = placeApiRepo.detailedPlace(place_ID, "")

        when(response.code()) {
            200 -> { /* TODO: Success Handling */ }
            else -> { /* TODO: Error Handling */ }
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

        firebaseRepo.getUserDBCollectionPath().child(place_ID).push().setValue(detailedData)
            .addOnCompleteListener {
                if (it.isSuccessful) { _pushDBResult.value = "Success" }
                else { it.exception?.printStackTrace() }
            }.addOnFailureListener { it.printStackTrace() }
    }

    fun getPhotoUrl(photoRef: String) =
        StringBuilder(Constant.IPlacePhotoAPIUri)
            .append("?maxwidth=${Constant.photoMaxWidth}")
            .append("&photo_reference=$photoRef")
            .append("&key=")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}