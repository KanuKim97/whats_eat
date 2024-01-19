package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.home.BannerItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMainBannerUseCase @Inject constructor(
    private val network: PlaceApiRepository,
) {
    operator fun invoke(
        latLng: String
    ): Flow<List<BannerItems>> = network
        .nearByPlace(latLng)
        .map { resultList ->
            resultList.map {
                BannerItems(
                    placeID = it.place_id,
                    name = it.name,
                    photoRef = if (it.photos.isEmpty()) {
                        ""
                    } else {
                        it.photos[0].photo_reference
                    }
                )
            }.slice(0..resultList.lastIndex/3)
        }
}