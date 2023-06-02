package com.example.domain.usecase.place

import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepositoryBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDetailPlaceItemUseCase @Inject constructor(
    private val placeApiProducer: PlaceApiRepositoryBase
) {
    operator fun invoke(
        place_id: String
    ): Flow<Results?> = placeApiProducer.detailedPlace(place_id)
}