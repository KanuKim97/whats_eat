package com.kanukim97.remote.services

import com.kanukim97.remote.constant.Constants
import com.kanukim97.remote.response.detailPlace.DetailedPlaceResponse
import com.kanukim97.remote.response.nearBySearch.NearBySearchResponse
import retrofit2.http.GET
import retrofit2.http.Query
import what_s_eat.core.remote.BuildConfig

interface PlaceServices {
    @GET("nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") latLng: String,
        @Query("radius") radius: String = Constants.LOCATION_RADIUS,
        @Query("type") type: String = Constants.LOCATION_TYPE,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("key") apiKey: String = BuildConfig.MAPS_API_KEY
    ): NearBySearchResponse

    @GET("details/json")
    suspend fun getDetails(
        @Query("place_id") placeID: String,
        @Query("language") language: String = Constants.LANGUAGE,
        @Query("key") apiKey: String = BuildConfig.MAPS_API_KEY
    ): DetailedPlaceResponse
}