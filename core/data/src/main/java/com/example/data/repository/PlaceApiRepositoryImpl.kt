package com.example.data.repository

import android.util.Log
import com.example.common.IODispatcher
import com.example.model.response.Results
import com.example.network.PlaceDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val network: PlaceDataSource,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
): PlaceApiRepository {
    override fun nearByPlace(latLng: String): Flow<ArrayList<Results>> = flow {
        val response = network.getNearBySearch(latLng)
        emit(response.results)
    }.flowOn(ioDispatcher)

    override fun detailedPlace(placeID: String): Flow<Results?> = flow {
        val response = network.getDetail(placeID)
        emit(response.result)
    }.flowOn(ioDispatcher)

}