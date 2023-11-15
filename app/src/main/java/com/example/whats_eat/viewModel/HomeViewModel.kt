package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.placeItem.response.Results
import com.example.domain.usecase.place.GetNearByPlaceUseCase
import com.example.whats_eat.common.Constant
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.presenter.adapter.adapterItems.MainBannerItems
import com.example.whats_eat.presenter.adapter.adapterItems.SubFoodItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearByPlaceUseCase: GetNearByPlaceUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _mainBannerItems = MutableLiveData<ArrayList<MainBannerItems>>()
    private val _subFoodItems = MutableLiveData<ArrayList<SubFoodItems>>()
    val mainBannerItems: LiveData<ArrayList<MainBannerItems>> get() = _mainBannerItems
    val subFoodItems: LiveData<ArrayList<SubFoodItems>> get() = _subFoodItems

    private val bannerItems: ArrayList<MainBannerItems> = arrayListOf()
    private val subItems: ArrayList<SubFoodItems> = arrayListOf()

    fun getMainBannerItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        getNearByPlaceUseCase(latLng).collect { results ->
            val resultsItems: List<Results> =
                results.sortedBy { it.rating }.slice(0..(results.lastIndex/2))

            resultsItems.forEach {
                bannerItems.add(
                    MainBannerItems(
                        it.place_id.toString(),
                        it.name.toString(),
                        getPhotoUrl(it.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
            _mainBannerItems.postValue(bannerItems)
        }
    }

    fun getSubGridViewItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        getNearByPlaceUseCase(latLng).collect { results ->
            results.forEach {
                subItems.add(
                    SubFoodItems(
                        it.place_id.toString(),
                        it.name.toString(),
                        getPhotoUrl(it.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
            _subFoodItems.postValue(subItems)
        }
    }

    private fun getPhotoUrl(photoReference: String): String =
        StringBuilder(Constant.PLACE_PHOTO_API_URI)
            .append("?maxwidth=1000")
            .append("&photo_reference=$photoReference")
            .append("&key=")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}