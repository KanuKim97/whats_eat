package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.entity.BannerItemsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMainBannerUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(
        latLng: String
    ): Flow<List<BannerItemsModel>> = network
        .nearByPlace(latLng)
        .map { resultList ->
            resultList.map {
                BannerItemsModel(
                    placeID = it.place_id,
                    name = it.name,
                    photoRef = it.photos[0].photo_reference
                )
            } .slice(0..resultList.lastIndex/3)
        }
}