package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.home.GridItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGridItemUseCase @Inject constructor(
    private val network: PlaceApiRepository,
) {
    operator fun invoke(
        latLng: String
    ): Flow<List<GridItems>> = network
        .nearByPlace(latLng)
        .map { resultList ->
            resultList.map {
                GridItems(
                    placeID = it.place_id,
                    name = it.name,
                    photoRef = if (it.photos.isEmpty()) {
                        ""
                    } else {
                        it.photos[0].photo_reference
                    }
                )
            }.slice(resultList.lastIndex/3 .. resultList.lastIndex)
        }
}