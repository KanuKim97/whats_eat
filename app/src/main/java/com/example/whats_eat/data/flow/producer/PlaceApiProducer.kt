package com.example.whats_eat.data.flow.producer

import com.example.whats_eat.BuildConfig
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.data.remote.model.detailPlace.PlaceDetail
import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.model.response.Results
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlaceApiProducer @Inject constructor(
    private val placeAPIService: IGoogleAPIService
) {
    fun nearByPlace(latLng: String): Flow<ArrayList<Results>> = flow {
        val response: Response<Myplaces> = placeAPIService.getNearPlaces(
            latLng,
            Constant.LOCATION_RADIUS,
            Constant.LOCATION_TYPE,
            BuildConfig.PLACE_API_KEY
        )

        if (response.isSuccessful && response.body()?.results != null) {
           emit(response.body()!!.results)
        } else {
            if (!response.isSuccessful) {
                throw (HttpException(response))
            }
            if (response.body()?.results == null) {
                throw (NullPointerException("Response data is Null"))
            }
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(arrayListOf())
        } else {
            throw exception
        }
    }.retryWhen { cause, attempt ->
        when {
            (cause is HttpException && attempt < 3) -> {
                delay(Constant.DELAY_TIME_MILLIS)
                true
            }
            else -> false
        }
    }

    fun detailedPlace(place_id: String): Flow<Results> = flow {
        val response: Response<PlaceDetail> =
            placeAPIService.getPlaceDetail(place_id, BuildConfig.PLACE_API_KEY)

        if (response.isSuccessful && response.body()?.result != null) {
            emit(response.body()!!.result!!)
        } else {
            if (!response.isSuccessful) {
                throw (HttpException(response))
            }
            if (response.body()?.result == null) {
                throw (NullPointerException("Response data is Null"))
            }
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(
                Results(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        } else {
            throw exception
        }
    }.retryWhen { cause, attempt ->
        when {
            (cause is HttpException && attempt < 3) -> {
                delay(Constant.DELAY_TIME_MILLIS)
                true
            }
            else -> false
        }
    }

}