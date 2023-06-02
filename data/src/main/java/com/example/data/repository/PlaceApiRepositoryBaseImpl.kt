package com.example.data.repository


import com.example.data.BuildConfig
import com.example.data.api.ApiParameter
import com.example.data.api.PlaceAPIService
import com.example.domain.model.placeItem.detailedPlace.DetailedPlace
import com.example.domain.model.placeItem.nearByPlace.MyPlaces
import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepositoryBase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlaceApiRepositoryBaseImpl @Inject constructor(
    private val placeApi: PlaceAPIService
): PlaceApiRepositoryBase {

    override fun nearByPlace(latLng: String): Flow<ArrayList<Results>> = flow {
        val response: Response<MyPlaces> = placeApi.getNearByPlaceItems(
            latLng,
            ApiParameter.LOCATION_RADIUS,
            ApiParameter.LOCATION_TYPE,
            BuildConfig.PLACE_API_KEY
        )
        if (response.isSuccessful && response.body()?.results != null) {
            val responseResults: ArrayList<Results>? = response.body()?.results
            emit(responseResults!!)
        } else {
            when {
                (!response.isSuccessful) -> throw(HttpException(response))
                (response.body()?.results) == null -> throw NullPointerException()
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
            (cause is HttpException && attempt < ApiParameter.RETRY_MAX_ATTEMPT) -> {
                delay(ApiParameter.DELAY_TIME_MILLIS)
                true
            }
            (cause is NullPointerException && attempt < ApiParameter.RETRY_MAX_ATTEMPT) -> {
                delay(ApiParameter.DELAY_TIME_MILLIS)
                true
            }
            else -> false
        }
    }

    override fun detailedPlace(place_Id: String): Flow<Results?> = flow {
        val response: Response<DetailedPlace> = placeApi.getDetailedPlaceItem(
            place_Id,
            BuildConfig.PLACE_API_KEY
        )

        if (response.isSuccessful && response.body()?.result != null) {
            val responseResult: Results? = response.body()?.result
            emit(responseResult)
        } else {
            when {
                (!response.isSuccessful) -> throw(HttpException(response))
                (response.body()?.result) == null -> throw NullPointerException()
            }
        }
    }.catch { exception ->
        if (exception is IOException) {
            emit(null)
        } else {
            throw exception
        }

    }. retryWhen { cause, attempt ->
        when {
            (cause is HttpException && attempt < ApiParameter.RETRY_MAX_ATTEMPT) -> {
                delay(ApiParameter.DELAY_TIME_MILLIS)
                true
            }
            (cause is NullPointerException && attempt < ApiParameter.RETRY_MAX_ATTEMPT) -> {
                delay(ApiParameter.DELAY_TIME_MILLIS)
                true
            }
            else -> false
        }
    }

}