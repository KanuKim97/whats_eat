package com.example.data.api

import com.example.domain.model.placeItem.detailedPlace.DetailedPlace
import com.example.domain.model.placeItem.nearByPlace.MyPlaces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceAPIService {
    @GET("nearbysearch/json")
    suspend fun getNearByPlaceItems(
        @Query("location")
        latLng: String,
        @Query("radius")
        radius: String,
        @Query("type")
        type: String,
        @Query("key")
        Api_Key: String
    ): Response<MyPlaces>

    @GET("details/json")
    suspend fun getDetailedPlaceItem(
        @Query("place_id")
        Place_ID: String,
        @Query("key")
        Api_key: String
    ): Response<DetailedPlace>
}