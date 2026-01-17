package com.kanukim97.domain.repository

import com.kanukim97.domain.entities.DetailRestaurantInformationResult
import com.kanukim97.domain.entities.NearByRestaurantResult
import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    fun getNearByRestaurant(latitude: Double, longitude: Double): Flow<List<NearByRestaurantResult>>

    fun getRestaurantInfo(id: String): Flow<DetailRestaurantInformationResult?>
}