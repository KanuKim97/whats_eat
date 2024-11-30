package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.domain.model.PlaceDetailItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(private val network: PlaceApiRepository) {
    operator fun invoke(placeId: String): Flow<PlaceDetailItemModel> = network
        .detailedPlace(placeId)
        .map { result ->
            PlaceDetailItemModel(
                placeId = result?.placeId.toString(),
                placeName = result?.name.toString(),
                placeRating = result?.rating.toString(),
                placeImgUrl = result?.photos?.let { photos ->
                    if (photos.isNotEmpty()) photos[0].getFullPhotoReference(BuildConfig.PLACE_API_KEY) else ""
                } ?: "",
                placeAddress = result?.formattedAddress.toString(),
                placePhoneNumber = result?.formattedPhoneNumber.toString(),
                placeLatitude = result?.geometry?.location?.lat ?: 0.0,
                placeLongitude = result?.geometry?.location?.lng ?: 0.0,
                isPlaceOpenNow = result?.openingHours?.openNow == true
            )
        }
}