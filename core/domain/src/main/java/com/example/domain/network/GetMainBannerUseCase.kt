package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.model.domain.BannerItemsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMainBannerUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(latLng: String): Flow<List<BannerItemsModel>> = network
        .nearByPlace(latLng)
        .map { resultList -> resultList.slice(0..resultList.lastIndex/3) }
        .map { result ->
            result.map {
                BannerItemsModel(
                    placeID = it.placeId ?: "",
                    name = it.name ?: "",
                    photoRef = it.photos[0].getFullPhotoReference(BuildConfig.PLACE_API_KEY)
                )
            }
        }
}