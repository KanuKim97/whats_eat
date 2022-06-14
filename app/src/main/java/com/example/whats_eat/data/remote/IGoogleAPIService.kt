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
    fun getNearbyPlaces(@Url url : String) : Call<Myplaces>

    @GET
    fun getDetailPlace(@Url url : String) : Call<PlaceDetail>

    //Test Code
    @GET("/json&radius={radius}&type={Type}&key={API_KEY}")
    fun getNearPlaces(
        @Query("location")
        latLng: String,
        @Path("radius")
        radius: String,
        @Path("Type")
        type: String,
        @Path("API_KEY")
        Api_Key: String
    ): Call<Myplaces>

    @GET("/json&photo_reference={photoRef}&key={API_KEY}")
    fun getPlaceDetail(
        @Query("maxwidth")
        maxWidth: String,
        @Path("photoRef")
        photoRef: String,
        @Path("API_KEY")
        Api_key: String
    ): Call<PlaceDetail>

}