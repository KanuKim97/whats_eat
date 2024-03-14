package com.example.network

import com.example.network.api.RetrofitPlaceAPI
import com.example.network.constant.Constants
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * NetworkUnitTest Class
 * Network local unit test, which will execute on the development machine (host).
 */
class NetworkUnitTest {
    private lateinit var network: RetrofitPlaceAPI

    // Default Latitude, Longitude
    private val defaultLatLng = "0.0, 0.0"
    // Default Place ID
    private val defaultPlaceID = "ChIJN1t_tDeuEmsRUsoyG83frY4"

    @Before
    fun init() {
        val okHttpClient = OkHttpClient()
            .newBuilder()
            .callTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectTimeout(Constants.TIMEOUT_SEC, TimeUnit.SECONDS)
            .build()

        val retrofitInstance = Retrofit
            .Builder()
            .baseUrl(Constants.PLACE_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        network = retrofitInstance.create(RetrofitPlaceAPI::class.java)
    }


    @Test
    fun get_nearbysearch_result() = runTest {
        val response = network.getNearBySearch(
            latLng = defaultLatLng,
            radius = Constants.LOCATION_RADIUS,
            type = Constants.LOCATION_TYPE,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )

        assertEquals(200, response.code())
        assertEquals("ZERO_RESULTS", response.body()?.status)
        println(response.body())
    }

    @Test
    fun get_detail_result() = runTest {
        val response = network.getDetails(
            placeID = defaultPlaceID,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )

        assertEquals(200, response.code())
        assertEquals("OK", response.body()?.status)
        println(response.body())
    }
}