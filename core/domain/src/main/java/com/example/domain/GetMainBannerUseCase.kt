package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.feature.BannerItemsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMainBannerUseCase @Inject constructor(
    private val network: PlaceApiRepository,
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
                    photoRef = if (it.photos.isEmpty()) {
                        ""
                    } else {
                        makePhotoRef(it.photos[0].photo_reference)
                    }
                )
            }.slice(0..resultList.lastIndex/3)
        }

    private fun makePhotoRef(photoRef: String): String {
        return StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=1000")
            .append("&photo_reference=${photoRef}")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()
    }
}