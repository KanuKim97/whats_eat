package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.domain.entity.GridItemsModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetGridItemUseCase @Inject constructor(
    private val network: PlaceApiRepository,
) {
    operator fun invoke(
        latLng: String
    ) = network
        .nearByPlace(latLng)
        .map { list ->
            list.map {
                GridItemsModel(
                    it.place_id,
                    it.name,
                    it.photos[0].photo_reference
                )
            }
        }
}