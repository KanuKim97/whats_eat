package com.example.whats_eat.data.remote

import com.example.whats_eat.data.model.nearByPlace.Myplaces
import com.example.whats_eat.data.model.detailPlace.PlaceDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url


interface IGoogleAPIService {

    @GET
    fun getDetailPlace(@Url url : String) : Call<PlaceDetail>


    @GET("/json")
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

    @GET("/json")
    fun getPlaceDetail(
        @Query("maxwidth")
        maxWidth: String,
        @Query("photoRef")
        photoRef: String,
        @Query("key")
        Api_key: String
    ): Call<PlaceDetail>

}