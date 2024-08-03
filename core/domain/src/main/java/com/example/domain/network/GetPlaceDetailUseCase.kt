package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.model.domain.DetailedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(placeId: String): Flow<DetailedModel> = network
        .detailedPlace(placeId)
        .map { value ->
            DetailedModel(
                placeId = value?.placeId.toString(),
                placeName = value?.name.toString(),
                placeImgUrl = value?.photos?.get(0)?.getFullPhotoReference(BuildConfig.PLACE_API_KEY) ?: "",
                placeRating = value?.rating.toString(),
                placeAddress = value?.formattedAddress.toString(),
                placePhoneNumber = value?.formattedPhoneNumber.toString(),
                placeLatitude = value?.geometry?.location?.lat ?: 0.0,
                placeLongitude = value?.geometry?.location?.lng ?: 0.0,
                isPlaceOpenNow = value?.openingHours?.openNow == true
            )
        }
}