package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.response.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetNearBySearchUseCase @Inject constructor(
    private val placeApiProducer: PlaceApiRepository
) {
    operator fun invoke(latLng: String): Flow<List<Results>> =
        placeApiProducer.nearByPlace(latLng).filter { result ->
            result.removeAll(
                result.filter { item ->
                    item.rating == 0.0 ||
                        item.rating == null ||
                        item.place_id == null ||
                        item.photos?.get(0)?.photo_reference == null
                }.toSet()
            )
        }
}