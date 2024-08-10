package com.example.network.api

import com.example.model.network.detailPlace.DetailedPlace
import com.example.model.network.nearBySearch.NearBySearch
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceApiService {
    @GET("nearbysearch/json")
    suspend fun getNearBySearch(
        @Query("location") latLng: String,
        @Query("radius") radius: String,
        @Query("type") type: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): NearBySearch

    @GET("details/json")
    suspend fun getDetails(
        @Query("place_id") placeID: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): DetailedPlace
}