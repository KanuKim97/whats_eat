package com.kanukim97.domain.usecases

import com.kanukim97.domain.entities.RestaurantInformation
import com.kanukim97.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetRestaurantInformationUseCase @Inject constructor(private val repository: RestaurantRepository) {
    operator fun invoke(id: String): Flow<RestaurantInformation?>{
        return repository
            .getRestaurantInfo(id)
            .mapNotNull { result ->
                if (result == null) return@mapNotNull null

                RestaurantInformation(
                    id = result.id,
                    name = result.name,
                    imageUrl = result.imageUrls.firstOrNull() ,
                    rating = result.rating?.toString() ?: "",
                    address = result.address,
                    reviewsCount = result.reviewCount,
                    phoneNumber = result.phoneNumber,
                    latitude = result.latitude,
                    longitude = result.longitude,
                    isOpenNow = result.isOpened,
                    url = result.url,
                    review = result.reviews?.slice(0..3) ?: emptyList()
                )
            }
    }
}