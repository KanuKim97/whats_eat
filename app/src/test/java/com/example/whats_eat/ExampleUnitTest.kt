package com.example.whats_eat

import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.AppRepository
import com.google.android.gms.maps.model.LatLng
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var appRepository: AppRepository

    @Before
    fun setUpRepos() { appRepository = AppRepository() }

   @Test
    suspend fun getNearByResult() {
        val response = appRepository.getNearby(
            latLng = LatLng(- 37.888, 151.024).toString(),
            radius = Constant.Location_Radius,
            type = Constant.Location_Type,
            Api_key = BuildConfig.GOOGLE_API_KEY
        ).code()

        assertEquals(response, 200)
    }
}