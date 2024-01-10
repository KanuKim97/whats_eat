package com.example.whats_eat.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetNearByPlaceUseCase
import com.example.model.response.Results
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.common.Constant
import com.example.whats_eat.di.IoDispatcher
import com.example.whats_eat.util.MainBannerItems
import com.example.whats_eat.util.MainGridItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNearByPlaceUseCase: GetNearByPlaceUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {
    private val _mainBannerItems = MutableStateFlow<ArrayList<MainBannerItems>>(arrayListOf())
    private val _mainGridItems = MutableStateFlow<ArrayList<MainGridItems>>(arrayListOf())
    val mainBannerItems: Flow<ArrayList<MainBannerItems>>
        get() = _mainBannerItems.asStateFlow()
    val mainGridItems: Flow<ArrayList<MainGridItems>>
        get() = _mainGridItems.asStateFlow()

    private val bannerItems: ArrayList<MainBannerItems> = arrayListOf()
    private val gridItems: ArrayList<MainGridItems> = arrayListOf()

    fun getMainBannerItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        getNearByPlaceUseCase(latLng).collect { results ->
            val resultsItems: List<Results>? = results?.sortedBy { items ->  items.rating }
                ?.slice(0..(results.lastIndex/2))

            resultsItems?.forEach {
                bannerItems.add(
                    MainBannerItems(
                        it.place_id.toString(),
                        it.name.toString(),
                        getPhotoUrl(it.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
            _mainBannerItems.value = bannerItems
        }
    }



    fun getSubGridViewItems(latLng: String): Job = viewModelScope.launch(ioDispatcher) {
        getNearByPlaceUseCase(latLng).collect { results ->
            results?.forEach {
                gridItems.add(
                    MainGridItems(
                        it.place_id.toString(),
                        it.name.toString(),
                        getPhotoUrl(it.photos?.get(0)?.photo_reference.toString())
                    )
                )
            }
            _mainGridItems.value = gridItems
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
        viewModelScope.cancel(cause = CancellationException("ViewModel onCleared"))
    }
}