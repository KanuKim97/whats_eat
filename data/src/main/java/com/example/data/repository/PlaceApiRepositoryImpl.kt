package com.example.data.repository


import com.example.data.BuildConfig
import com.example.data.api.ApiParameter
import com.example.data.api.PlaceAPIService
import com.example.domain.model.placeItem.detailedPlace.DetailedPlace
import com.example.domain.model.placeItem.nearByPlace.MyPlaces
import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val placeApi: PlaceAPIService
): PlaceApiRepository {
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
    }.filter { items ->
        items.removeAll(
            items.filter { item ->
                item.rating == 0.0 ||
                        item.rating == null ||
                        item.place_id == null ||
                        item.photos?.get(0)?.photo_reference == null
            }.toSet()
        )
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