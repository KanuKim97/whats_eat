package com.example.domain

import com.example.data.repository.PlaceApiRepository
import com.example.model.response.Results
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaceDetailUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(placeId: String): Flow<Results?> =
        network.detailedPlace(placeId)
}