package com.kanukim97.domain.usecases

import com.kanukim97.data.repository.PlaceRepository
import com.kanukim97.domain.entities.Place
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlaceItemsUseCase @Inject constructor(private val repository: PlaceRepository) {
    operator fun invoke(latLng: String): Flow<List<Place>> {
        return repository
            .getNearByPlace(latLng)
            .map { result ->
                if (result.isEmpty()) return@map emptyList<Place>()

                result
                    .slice(result.lastIndex / 3 .. result.lastIndex)
                    .map { place ->
                        Place(
                            id = place.id,
                            name = place.name,
                            rating = place.rating,
                            latitude = place.latitude,
                            longitude = place.longitude,
                            imageUrl = place.imageUrls.firstOrNull()
                        )
                    }
            }
    }
}