package com.example.whats_eat.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url:String) : Call<>
}