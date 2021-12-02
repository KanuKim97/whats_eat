package com.example.whats_eat.remote

import com.example.whats_eat.Model.Myplaces
import com.example.whats_eat.Model.placeDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url


interface IGoogleAPIService {
    @GET
    fun getNearbyPlaces(@Url url : String) : Call<Myplaces>

    @GET
    fun getDetailPlace(@Url url : String) : Call<placeDetail>
}