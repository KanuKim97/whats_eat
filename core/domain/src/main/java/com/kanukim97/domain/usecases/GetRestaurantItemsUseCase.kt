package com.kanukim97.domain.usecases

import com.kanukim97.domain.entities.RestaurantItems
import com.kanukim97.domain.repository.RestaurantRepository
import com.kanukim97.domain.util.calculateDistance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRestaurantItemsUseCase @Inject constructor(private val repository: RestaurantRepository) {
    operator fun invoke(latitude: Double, longitude: Double): Flow<List<RestaurantItems>> {
        return repository
            .getNearByRestaurant(latitude = latitude, longitude = longitude)
            .map { results ->
                if (results.isEmpty()) return@map emptyList<RestaurantItems>()

                results.map {
                    RestaurantItems(
                        id = it.id,
                        name = it.name,
                        rating = it.rating?.toString() ?: "",
                        reviewsCount = it.reviewCount,
                        imageUrl = it.imageUrls.firstOrNull(),
                        distance = calculateDistance(
                            startLat = latitude,
                            startLng = longitude,
                            endLat = it.latitude,
                            endLng = it.longitude
                        )
                    )
                }
            }
    }
}