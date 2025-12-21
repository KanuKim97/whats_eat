package com.kanukim97.data.impl

import com.kanukim97.data.BuildConfig
import com.kanukim97.data.exception.InvalidRequestException
import com.kanukim97.data.exception.QueryLimitException
import com.kanukim97.data.exception.RequestDeniedException
import com.kanukim97.data.mapper.toDataModel
import com.kanukim97.data.model.DetailPlaceResult
import com.kanukim97.data.model.NearByPlaceResult
import com.kanukim97.data.repository.PlaceRepository
import com.kanukim97.remote.services.PlaceServices
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(private val network: PlaceServices): PlaceRepository {
    override fun getNearByPlace(latLng: String): Flow<List<NearByPlaceResult>> = flow {
        val response = network.getNearbyPlaces(latLng)

        when (response.status) {
            "OK" -> {
                val result = response.results.map { item ->
                    NearByPlaceResult(
                        id = item.placeId ?: "",
                        name = item.name ?: "",
                        imageUrls = with(item.photos) {
                            if (this.isEmpty()) return@with emptyList()

                            this.map { item -> item.getFullPhotoReference(BuildConfig.API_KEY) }
                        },
                        rating = item.rating,
                        latitude = item.geometry?.location?.lat ?: 0.0,
                        longitude = item.geometry?.location?.lng ?: 0.0,
                        ref = item.reference,
                        vicinity = item.vicinity,
                    )
                }

                emit(result)
            }
            "ZERO_RESULTS" -> {
                emit(emptyList())
            }
            "OVER_QUERY_LIMIT" -> {
                throw QueryLimitException(response.status)
            }
            "REQUEST_DENIED" -> {
                throw RequestDeniedException(response.status)
            }
            "INVALID_REQUEST" -> {
                throw InvalidRequestException(response.status)
            }
            "UNKNOWN_ERROR" -> {
                throw UnknownError(response.status)
            }
            else -> {
                throw Exception(response.status)
            }
        }
    }

    override fun getPlaceDetail(id: String): Flow<DetailPlaceResult?> = flow {
        val response = network.getDetails(id)

        when (response.status) {
            "OK" -> {
                val result = response.result.toDataModel()

                emit(result)
            }
            "NOT_FOUND" -> {
                emit(null)
            }
            "OVER_QUERY_LIMIT" -> {
                throw QueryLimitException(response.status)
            }
            "REQUEST_DENIED" -> {
                throw RequestDeniedException(response.status)
            }
            "INVALID_REQUEST" -> {
                throw InvalidRequestException(response.status)
            }
            "UNKNOWN_ERROR" -> {
                throw UnknownError(response.status)
            }
            else -> {
                throw Exception(response.status)
            }
        }
    }

}