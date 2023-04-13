package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.data.di.repository.PlaceApiRepository
import com.example.whats_eat.data.remote.model.responseModel.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeApiRepo: PlaceApiRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _nearByPlace = MutableLiveData<Array<Results>>()
    val nearByPlace: LiveData<Array<Results>> get() = _nearByPlace

    fun searchNearByPlace(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        val response = placeApiRepo.nearByPlace(latLng)

        when {
            !response.isSuccessful ->{
                this.cancel()
                throw HttpException(response)
            }
            response.body()?.results == null ->
                throw NullPointerException("Result is Null")
            else ->
                _nearByPlace.postValue(response.body()!!.results)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}