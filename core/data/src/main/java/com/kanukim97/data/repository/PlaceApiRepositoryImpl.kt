package com.kanukim97.data.repository

import com.kanukim97.model.network.nearBySearch.NearBySearchResult
import com.kanukim97.model.network.detailPlace.DetailedResult
import com.kanukim97.network.PlaceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val network: PlaceDataSource,
): PlaceApiRepository {
    override fun nearByPlace(latLng: String): Flow<List<NearBySearchResult>> = flow {
        val response = network.getNearBySearch(latLng)
        emit(response.results)
    }

    override fun detailedPlace(placeID: String): Flow<DetailedResult?> = flow {
        val response = network.getDetail(placeID)
        emit(response.result)
    }

}