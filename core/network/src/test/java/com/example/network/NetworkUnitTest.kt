package com.example.network

import com.example.network.api.PlaceApiService
import com.example.network.constant.Constants
import com.example.network.util.addDefaultTimeOut
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

/**
 * NetworkUnitTest Class
 * Network local unit test, which will execute on the development machine (host).
 */
class NetworkUnitTest {
    private val json by lazy { Json { ignoreUnknownKeys = true } }

    private lateinit var httpClient: OkHttpClient
    private lateinit var retrofitClient: Retrofit
    private lateinit var network: PlaceApiService

    @Before
    fun init() {
        httpClient = OkHttpClient()
            .newBuilder()
            .addDefaultTimeOut()
            .build()

        retrofitClient = Retrofit
            .Builder()
            .baseUrl(Constants.PLACE_API_BASE_URL)
            .client(httpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        network = retrofitClient.create(PlaceApiService::class.java)
    }

    @Test
    fun `Get Near By Search Result When Result Status is Success`() = runTest {
        val response = network.getNearBySearch(
            latLng = NetworkLayerDummyData.DUMMY_LAT_LNG,
            radius = Constants.LOCATION_RADIUS,
            type = Constants.LOCATION_TYPE,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )

        assertEquals("OK", response.status)
    }

    @Test
    fun `Get Detail Place Result When Result Status is Success`() = runTest {
        val response = network.getDetails(
            placeID = NetworkLayerDummyData.DUMMY_PLACE_ID,
            language = Constants.LANGUAGE,
            apiKey = BuildConfig.PLACE_API_KEY
        )

        assertEquals("OK", response.status)
    }
}