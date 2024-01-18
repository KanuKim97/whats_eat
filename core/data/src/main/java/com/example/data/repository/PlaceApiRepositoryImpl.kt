package com.example.data.repository

import com.example.model.response.Results
import com.example.network.PlaceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val network: PlaceDataSource,
): PlaceApiRepository {
    override fun nearByPlace(latLng: String): Flow<ArrayList<Results>> = flow {
        val response = network.getNearBySearch(latLng)


        emit(response.results)
    }

    override fun detailedPlace(placeID: String): Flow<Results?> = flow {
        val response = network.getDetail(placeID)

        emit(response.result)
    }

}