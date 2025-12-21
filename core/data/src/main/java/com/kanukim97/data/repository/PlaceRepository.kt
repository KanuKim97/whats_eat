package com.kanukim97.data.repository

import com.kanukim97.data.model.DetailPlaceResult
import com.kanukim97.data.model.NearByPlaceResult
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getNearByPlace(latLng: String): Flow<List<NearByPlaceResult>>

    fun getPlaceDetail(id: String): Flow<DetailPlaceResult?>
}