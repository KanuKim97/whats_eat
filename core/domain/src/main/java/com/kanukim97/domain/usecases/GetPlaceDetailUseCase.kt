package com.kanukim97.domain.usecases

import com.kanukim97.data.repository.PlaceRepository
import com.kanukim97.domain.entities.PlaceDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(private val repository: PlaceRepository) {
    operator fun invoke(id: String): Flow<PlaceDetail?>{
        return repository
            .getPlaceDetail(id)
            .mapNotNull { result ->
                if (result == null) return@mapNotNull null

                PlaceDetail(
                    id = result.id,
                    name = result.name,
                    imageUrl = result.imageUrls.firstOrNull() ,
                    rating = result.rating?.toString() ?: "",
                    address = result.address,
                    phoneNumber = result.phoneNumber,
                    latitude = result.latitude,
                    longitude = result.longitude,
                    isOpenNow = result.isOpened,
                    url = result.url
                )
            }
    }
}