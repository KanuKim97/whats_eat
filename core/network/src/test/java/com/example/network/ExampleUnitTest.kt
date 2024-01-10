package com.example.network

import com.example.network.api.RetrofitPlaceAPI
import com.example.network.constant.Constants
import okhttp3.OkHttpClient
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var retrofit: RetrofitPlaceAPI

    @Before
    fun init() {
        val retrofitInstance = Retrofit.Builder()
            .baseUrl(Constants.PLACE_API_BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit = retrofitInstance.create(RetrofitPlaceAPI::class.java)
    }

    @Test
    suspend fun nearBySearch() {
        retrofit.getNearBySearch(
            latLng = "37.55189, 126.9917933",
            radius = Constants.LOCATION_RADIUS,
            type = Constants.LOCATION_TYPE,
            apiKey = BuildConfig.PLACE_API_KEY
        ).body()
    }
}