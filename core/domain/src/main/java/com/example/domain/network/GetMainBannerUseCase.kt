package com.example.domain.network

import com.example.data.repository.PlaceApiRepository
import com.example.model.nearBySearch.NearBySearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMainBannerUseCase @Inject constructor(
    private val network: PlaceApiRepository
) {
    operator fun invoke(latLng: String): Flow<List<NearBySearchResult>> = network
        .nearByPlace(latLng)
        .map { resultList -> resultList.slice(0..resultList.lastIndex/3) }
}