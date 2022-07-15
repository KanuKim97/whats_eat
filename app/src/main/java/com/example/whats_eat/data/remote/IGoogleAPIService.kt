package com.example.whats_eat.data.remote

import com.example.whats_eat.data.model.nearByPlace.Myplaces
import com.example.whats_eat.data.model.detailPlace.PlaceDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface IGoogleAPIService {

    @GET("json")
    fun getNearPlaces(
        @Query("location")
        latLng: String,
        @Query("radius")
        radius: String,
        @Query("type")
        type: String,
        @Query("key")
        Api_Key: String
    ): Call<Myplaces>

    @GET("json")
    fun getPlaceDetail(
        @Query("place_id")
        Place_ID: String,
        @Query("key")
        Api_key: String
    ): Call<PlaceDetail>

}