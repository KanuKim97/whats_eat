package com.example.whats_eat.data.remote

import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.model.detailPlace.PlaceDetail
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/* Google Api Service Interface (Retrofit) */
interface IGoogleAPIService {
    @GET("nearbysearch/json")
    suspend fun getNearPlaces(
        @Query("location")
        latLng: String,
        @Query("radius")
        radius: String,
        @Query("type")
        type: String,
        @Query("key")
        Api_Key: String
    ): Response<Myplaces>

    @GET("details/json")
   suspend fun getPlaceDetail(
        @Query("place_id")
        Place_ID: String,
        @Query("key")
        Api_key: String
    ): Response<PlaceDetail>
}