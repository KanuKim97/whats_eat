package com.example.whats_eat.PlaceViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.Model.Results

class PlaceViewModel : ViewModel() {

    private val _placeValue = MutableLiveData<String>()

    init {
        Log.d("View Model Log", "View Model - init")
        _placeValue.value = null
    }


    fun updateValue(googlePlace : Results?){
        Log.d("Value lat", googlePlace?.geometry!!.location!!.lat.toString())
        Log.d("Value lng", googlePlace.geometry!!.location!!.lng.toString())
        Log.d("Value Name", googlePlace.name.toString())
    }
}