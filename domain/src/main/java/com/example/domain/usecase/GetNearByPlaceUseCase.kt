package com.example.domain.usecase

import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetNearByPlaceUseCase @Inject constructor(
    private val placeApiProducer: PlaceApiRepository
) {
    operator fun invoke(
        latLng: String
    ): Flow<ArrayList<Results>> = placeApiProducer.nearByPlace(latLng).filter {
        it.removeAll(
            it.filter { item ->
                item.rating == 0.0 ||
                        item.rating == null ||
                        item.place_id == null ||
                        item.photos?.get(0)?.photo_reference == null
            }.toSet()
        )
    }
}