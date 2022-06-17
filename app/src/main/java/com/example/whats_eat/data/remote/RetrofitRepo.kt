package com.example.whats_eat.data.remote

import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.detailPlace.PlaceDetail
import com.example.whats_eat.data.model.nearByPlace.Myplaces
import retrofit2.Call

object RetrofitRepo {

    fun getNearbySingleton(
        latLng: String,
        radius: String,
        type: String,
        Api_key: String
    ): Call<Myplaces> {

        return RetrofitClient
            .getClient(Constant.INearPlaceAPIUri)
            .create(IGoogleAPIService::class.java)
            .getNearPlaces(latLng, radius, type, Api_key)

    }

    fun getPlaceDetailSingleton(
        maxWidth: String,
        photoRef: String,
        Api_key: String
    ): Call<PlaceDetail> {

        return RetrofitClient
            .getClient(Constant.IPlaceDetailAPIUri)
            .create(IGoogleAPIService::class.java)
            .getPlaceDetail(maxWidth, photoRef, Api_key)

    }

}