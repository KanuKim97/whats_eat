package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.feature.DetailedModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(
        placeId: String
    ) = network
        .detailedPlace(placeId)
        .map { value ->
            DetailedModel(
                placeId = value?.place_id.toString(),
                placeName = value?.name.toString(),
                placeImgUrl = makePhotoRef(value?.photos?.get(0)?.photo_reference ?: ""),
                placeLatitude = value?.geometry?.location?.lat ?: 0.0,
                placeLongitude = value?.geometry?.location?.lng ?: 0.0,
                isPlaceOpenNow = value?.opening_hours?.open_now == true
            )
        }

    private fun makePhotoRef(photoRef: String): String {
        return StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=1000")
            .append("&photo_reference=${photoRef}")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()
    }
}