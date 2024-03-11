package com.example.data

import com.example.data.repository.PlaceApiRepositoryImpl
import com.example.model.nearBySearch.NearBySearchResult
import com.example.network.PlaceDataSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify


/**
 * PlaceRepo local unit test
 * TODO("Test is failed, Issue")
 */
class PlaceRepoUnitTest {
    @Mock
    private lateinit var network: PlaceDataSource
    private lateinit var placeApiRepositoryImpl: PlaceApiRepositoryImpl

    private val defaultLatLng = "0.0, 0.0"
    private val defaultPlaceID = "ChIJN1t_tDeuEmsRUsoyG83frY4"
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        placeApiRepositoryImpl = PlaceApiRepositoryImpl(network, testDispatcher)
    }

    @Test
    fun `execute should return nearByPlace from Repository`() = runBlocking {
        var result = listOf<NearBySearchResult>()

        `when`(placeApiRepositoryImpl.nearByPlace(defaultLatLng).collect()).thenReturn(Unit)

        placeApiRepositoryImpl.nearByPlace(defaultLatLng).collect{ result = it }

        assertEquals(
            listOf<NearBySearchResult>(),
            result
        )
        verify(placeApiRepositoryImpl.detailedPlace(defaultPlaceID)).collect()
    }
}

