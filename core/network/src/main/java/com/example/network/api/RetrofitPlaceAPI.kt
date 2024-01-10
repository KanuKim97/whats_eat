package com.example.network.api

import com.example.model.details.DetailedPlace
import com.example.model.nearBySearch.MyPlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPlaceAPI {
    @GET("nearbysearch/json")
    suspend fun getNearBySearch(
        @Query("location") latLng: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): Response<MyPlaces>

    @GET("details")
    suspend fun getDetails(
        @Query("place_id") placeID: String,
        @Query("key") apiKey: String
    ): Response<DetailedPlace>
}