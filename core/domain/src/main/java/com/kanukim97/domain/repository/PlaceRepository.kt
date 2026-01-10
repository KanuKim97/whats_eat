package com.kanukim97.domain.repository

import com.kanukim97.domain.entities.DetailPlaceResult
import com.kanukim97.domain.entities.NearByPlaceResult
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    fun getNearByPlace(latLng: String): Flow<List<NearByPlaceResult>>

    fun getPlaceDetail(id: String): Flow<DetailPlaceResult?>
}