package com.kanukim97.network

import com.kanukim97.model.network.detailPlace.DetailedPlace
import com.kanukim97.model.network.nearBySearch.NearBySearch
import com.kanukim97.network.api.PlaceApiService
import com.kanukim97.network.constant.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceDataSourceImpl @Inject constructor(
    private val placeApiService: PlaceApiService
): PlaceDataSource {

    override suspend fun getDetail(placeID: String): DetailedPlace =
        placeApiService.getDetails(
            placeID = placeID,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )

    override suspend fun getNearBySearch(latLng: String): NearBySearch {
        val result = placeApiService.getNearBySearch(
            latLng = latLng,
            radius = Constants.LOCATION_RADIUS,
            type = Constants.LOCATION_TYPE,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )
        return result
    }
}