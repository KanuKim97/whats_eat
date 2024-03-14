package com.example.data

import com.example.data.repository.PlaceApiRepository
import com.example.data.sampleDBdata.testSampleDetailPlace
import com.example.model.details.DetailedResult
import com.example.model.nearBySearch.NearBySearchResult
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PlaceRepoUnitTest {
    private val placeApiRepositoryImpl = mockk<PlaceApiRepository>()

    private val defaultLatLng = "0.0, 0.0"
    private val defaultPlaceID = "ChIJN1t_tDeuEmsRUsoyG83frY4"

    @Before
    fun initRepositoryFunctionsBehavior() {
        every { placeApiRepositoryImpl.nearByPlace(defaultLatLng) } returns flowOf(listOf())
        every { placeApiRepositoryImpl.detailedPlace(defaultPlaceID) } returns flowOf(testSampleDetailPlace)
    }

    @Test
    fun `execute should return empty list from Repository nearByPlace function`() = runBlocking {
        var result = listOf<NearBySearchResult>()

        placeApiRepositoryImpl.nearByPlace(defaultLatLng).collect{ result = it }

        assertEquals(
            result,
            listOf<NearBySearchResult>()
        )
    }

    @Test
    fun `execute should return DetailedResult from Repository Detailed Place function`() = runBlocking {
        var result: DetailedResult? = null

        placeApiRepositoryImpl.detailedPlace(defaultPlaceID).collect{ result = it }

        assertEquals(
            result,
            testSampleDetailPlace
        )
    }

    @After
    fun clearRepositoryMocking() {
        clearMocks(placeApiRepositoryImpl)
    }
}

