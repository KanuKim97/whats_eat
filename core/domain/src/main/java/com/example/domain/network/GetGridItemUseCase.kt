package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.domain.model.NearByPlaceItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGridItemUseCase @Inject constructor(private val network: PlaceApiRepository) {
    operator fun invoke(latLng: String): Flow<List<NearByPlaceItemModel>> = network
        .nearByPlace(latLng)
        .map { list -> list.slice(list.lastIndex/3 .. list.lastIndex) }
        .map { result ->
            result.map {
                NearByPlaceItemModel(
                    placeId = it.placeId ?: "",
                    placeName = it.name ?: "",
                    placePhotoReference = if (it.photos.isNotEmpty()) {
                        it.photos[0].getFullPhotoReference(BuildConfig.PLACE_API_KEY)
                    } else {
                        ""
                    }
                )
            }
        }
}