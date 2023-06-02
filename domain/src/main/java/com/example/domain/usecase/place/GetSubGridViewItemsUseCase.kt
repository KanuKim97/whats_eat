package com.example.domain.usecase.place

import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSubGridViewItemsUseCase @Inject constructor(
    private val placeApiIntermediary: PlaceApiRepository
) {
    operator fun invoke(
        latLng: String
    ): Flow<ArrayList<Results>> = placeApiIntermediary.getSubBannerItems(latLng)
}