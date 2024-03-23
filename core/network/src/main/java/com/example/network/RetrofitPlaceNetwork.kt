package com.example.network

import com.example.model.details.DetailedPlace
import com.example.model.nearBySearch.NearBySearch
import com.example.network.api.RetrofitPlaceAPI
import com.example.network.constant.Constants
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitPlaceNetwork @Inject constructor(retrofit: Retrofit): PlaceDataSource {
    private val placeApi: RetrofitPlaceAPI = retrofit.create(RetrofitPlaceAPI::class.java)

    override suspend fun getDetail(placeID: String): DetailedPlace =
        placeApi.getDetails(
            placeID = placeID,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        ).body()!!

    override suspend fun getNearBySearch(latLng: String): NearBySearch =
        placeApi.getNearBySearch(
            latLng = latLng,
            radius = Constants.LOCATION_RADIUS,
            type = Constants.LOCATION_TYPE,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        ).body()!!
}