package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.model.responseModel.Results
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MapsViewModel(private val appRepo: AppRepository): ViewModel() {
    private var _mapResponseData = MutableLiveData<Myplaces?>()

    val mapResponseData: LiveData<Myplaces?>
        get() = _mapResponseData

    fun getNearByPlace(curLatLng: LatLng) = viewModelScope.launch(Dispatchers.IO) {
        val response = appRepo.getNearby(
            latLng = curLatLng.toString(),
            radius = Constant.Location_Radius,
            type = Constant.Location_Type,
            Api_key = BuildConfig.GOOGLE_API_KEY
        )

        when (response.code()) {
            200 -> {
                try {
                    val currentPlace = response.body()
                    if (currentPlace?.results != null) { _mapResponseData.value = currentPlace }
                } catch (e: NullPointerException) { e.printStackTrace() }
            }
            else -> { /* TODO: Error Handling */}
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}