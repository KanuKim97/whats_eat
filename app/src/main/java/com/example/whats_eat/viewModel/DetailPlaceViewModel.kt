package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.producer.PlaceApiProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPlaceViewModel @Inject constructor(
    private val placeApiProducer: PlaceApiProducer,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {


    fun getPlaceDetailData(placeID: String): Job = viewModelScope.launch(ioDispatcher) {
        placeApiProducer.detailedPlace(placeID).collect { result ->
            result.name
            result.formatted_address
            result.openingHours?.open_now
            result.photos?.get(0)?.photo_reference
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}