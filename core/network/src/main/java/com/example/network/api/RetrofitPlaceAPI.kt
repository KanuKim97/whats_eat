package com.example.network.api

import com.example.model.details.DetailedPlace
import com.example.model.nearBySearch.NearBySearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitPlaceAPI {
    @GET("nearbysearch/json")
    suspend fun getNearBySearch(
        @Query("location") latLng: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): Response<NearBySearch>

    @GET("details/json")
    suspend fun getDetails(
        @Query("place_id") placeID: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): Response<DetailedPlace>
}