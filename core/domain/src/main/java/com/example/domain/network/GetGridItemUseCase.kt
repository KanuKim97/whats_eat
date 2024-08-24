package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.BuildConfig
import com.example.model.domain.GridItemsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGridItemUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(latLng: String): Flow<List<GridItemsModel>> = network
        .nearByPlace(latLng)
        .map { list -> list.slice(list.lastIndex/3 .. list.lastIndex) }
        .map { result ->
            result.map {
                GridItemsModel(
                    placeID = it.placeId ?: "",
                    name = it.name ?: "",
                    photoRef = if (it.photos.isNotEmpty()) {
                        it.photos[0].getFullPhotoReference(BuildConfig.PLACE_API_KEY)
                    } else {
                        ""
                    }
                )
            }
        }
}