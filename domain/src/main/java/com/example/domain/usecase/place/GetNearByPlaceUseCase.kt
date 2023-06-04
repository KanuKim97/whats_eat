package com.example.domain.usecase.place

import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearByPlaceUseCase @Inject constructor(
    private val placeApiProducer: PlaceApiRepository
) {
    operator fun invoke(
        latLng: String
    ): Flow<ArrayList<Results>> = placeApiProducer.nearByPlace(latLng)
}