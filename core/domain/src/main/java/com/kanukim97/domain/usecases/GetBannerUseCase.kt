package com.kanukim97.domain.usecases

import com.kanukim97.domain.entities.Banner
import com.kanukim97.domain.repository.PlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBannerUseCase @Inject constructor(private val repository: PlaceRepository) {
    operator fun invoke(latLng: String): Flow<List<Banner>> {
        return repository
            .getNearByPlace(latLng)
            .map { result ->
                if (result.isEmpty()) return@map emptyList<Banner>()

                result
                    .slice(0..result.lastIndex / 3)
                    .map {
                    Banner(
                        id = it.id,
                        name = it.name,
                        imageUrl = it.imageUrls.firstOrNull() ?: ""
                    )
                }
            }
    }
}