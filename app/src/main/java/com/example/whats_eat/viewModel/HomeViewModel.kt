package com.example.whats_eat.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.data.flow.intermediary.PlaceApiIntermediary
import com.example.whats_eat.view.dataViewClass.MainBannerItems
import com.example.whats_eat.view.dataViewClass.SubFoodItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeApiIntermediary: PlaceApiIntermediary,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _mainBannerItems = MutableLiveData<ArrayList<MainBannerItems>>()
    private val _subFoodItems = MutableLiveData<ArrayList<SubFoodItems>>()
    val mainBannerItems: LiveData<ArrayList<MainBannerItems>> get() = _mainBannerItems
    val subFoodItems: LiveData<ArrayList<SubFoodItems>> get() = _subFoodItems

    private val bannerItems: ArrayList<MainBannerItems> = arrayListOf()
    private val subItems: ArrayList<SubFoodItems> = arrayListOf()

    fun getMainBannerItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        placeApiIntermediary.getMainBannerItem(latLng).collect { results ->
            val result = results.sortedBy { it.rating }.slice(0..(results.lastIndex/2))

            for (element in result) {
                bannerItems.add(
                    MainBannerItems(
                        element.place_id.toString(),
                        element.name.toString(),
                        getPhotoUrl(element.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
            _mainBannerItems.postValue(bannerItems)
        }
    }

    fun getSubFoodItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        placeApiIntermediary.getSubBannerItem(latLng).collect { results ->
            for (element in results) {
                subItems.add(
                    SubFoodItems(
                        element.name.toString(),
                        getPhotoUrl(element.photos?.get(0)?.photo_reference.toString())
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
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}