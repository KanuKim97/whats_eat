package com.example.data.repository


import com.example.data.BuildConfig
import com.example.data.api.ApiParameter
import com.example.data.api.PlaceAPIService
import com.example.data.placeModel.nearByPlace.MyPlaces
import com.example.domain.model.PlaceItem
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val placeApi: PlaceAPIService
): PlaceApiRepository {

    override fun getMainBannerItems(
        latLng: String
    ): Flow<ArrayList<PlaceItem?>> = flow<ArrayList<PlaceItem?>> {
        val response: Response<MyPlaces> = placeApi.getNearByPlaceItems(
            latLng,
            ApiParameter.LOCATION_RADIUS,
            ApiParameter.LOCATION_TYPE,
            BuildConfig.BUILD_TYPE
        )

        if (response.isSuccessful && response.body()?.results != null) {
            response.body()?.results!!.forEach { items ->

            }
        } else {
            when {
                !response.isSuccessful -> throw(HttpException(response))
                response.body()?.results == null -> throw NullPointerException()
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
                true
            }
            (cause is NullPointerException && attempt < 3) -> {
                true
            }
            else -> false
        }
    }

    override fun getSubBannerItems(
        latLng: String
    ): Flow<ArrayList<PlaceItem?>> = flow <ArrayList<PlaceItem?>>{

    }.catch { exception ->
        if (exception is IOException) {
            emit(arrayListOf())
        } else {
            throw exception
        }
    }.retryWhen { cause, attempt ->
        when {
            (cause is HttpException && attempt < 3) -> {
                true
            }
            (cause is NullPointerException && attempt < 3) -> {
                true
            }
            else -> false
        }
    }

}