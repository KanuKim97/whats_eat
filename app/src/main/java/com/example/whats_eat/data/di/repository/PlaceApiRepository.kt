package com.example.whats_eat.data.di.repository

import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.data.remote.model.detailPlace.PlaceDetail
import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import retrofit2.Response
import javax.inject.Inject

/* Google Place Api Service Repository */
class PlaceApiRepository @Inject constructor(
    private val placeAPIService: IGoogleAPIService
) {
    suspend fun nearByPlace(latLng: String): Response<Myplaces> =
        placeAPIService.getNearPlaces(
            latLng,
            Constant.LOCATION_RADIUS,
            Constant.LOCATION_TYPE,
            BuildConfig.PLACE_API_KEY
        )

    suspend fun detailedPlace(place_id: String): Response<PlaceDetail> =
        placeAPIService.getPlaceDetail(place_id, BuildConfig.PLACE_API_KEY)
}