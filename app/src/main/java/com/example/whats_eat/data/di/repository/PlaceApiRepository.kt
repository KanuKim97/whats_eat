package com.example.whats_eat.data.di.repository

import com.example.whats_eat.data.remote.IGoogleAPIService
import javax.inject.Inject

/* Google Place Api Service Repository */
class PlaceApiRepository @Inject constructor(
    private val placeAPIService: IGoogleAPIService
) {
    suspend fun nearByPlace(
        latLng: String,
        radius: String,
        type: String,
        Api_Key: String
    ) = placeAPIService.getNearPlaces(latLng, radius, type, Api_Key)

    suspend fun detailedPlace(
        place_id: String,
        Api_Key: String
    ) = placeAPIService.getPlaceDetail(place_id, Api_Key)
}