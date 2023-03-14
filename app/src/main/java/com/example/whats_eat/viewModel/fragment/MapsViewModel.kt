package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.data.di.repository.PlaceApiRepository
import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val placeApiRepo: PlaceApiRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _nearByResponse = MutableLiveData<Myplaces>()
    val nearByResponse: LiveData<Myplaces?> get() = _nearByResponse

    fun searchNearByPlace() = viewModelScope.launch(ioDispatcher) {
        val response = placeApiRepo.nearByPlace(
            Constant.defaultLatLng,
            Constant.Location_Radius,
            Constant.Location_Type,
            BuildConfig.MAPS_API_KEY
        )

        if(response.isSuccessful && response.body() != null) {
            _nearByResponse.postValue(response.body())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}