package com.example.network

import com.example.model.network.detailPlace.DetailedPlace
import com.example.model.network.nearBySearch.NearBySearch
import com.example.network.api.PlaceApiService
import com.example.network.constant.Constants
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