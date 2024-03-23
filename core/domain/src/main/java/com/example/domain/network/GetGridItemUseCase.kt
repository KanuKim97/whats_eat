package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.domain.entity.GridItemsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGridItemUseCase @Inject constructor(
    private val network: PlaceApiRepository,
) {
    operator fun invoke(
        latLng: String
    ): Flow<List<GridItemsModel>> = network
        .nearByPlace(latLng)
        .map { list ->
            list.map {
                GridItemsModel(
                    placeID = it.place_id,
                    name = it.name,
                    photoRef = makePhotoRef(it.photos[0].photo_reference)
                )
            }
        }

    private fun makePhotoRef(photoRef: String): String {
        return StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=1000")
            .append("&photo_reference=${photoRef}")
            .append("&key=${BuildConfig.PLACE_API_KEY}")
            .toString()
    }
}