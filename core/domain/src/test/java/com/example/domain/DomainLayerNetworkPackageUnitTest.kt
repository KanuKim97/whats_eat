package com.example.domain

import com.example.model.domain.BannerItemsModel
import com.example.model.domain.DetailedModel
import com.example.model.domain.GridItemsModel
import com.example.domain.network.GetGridItemUseCase
import com.example.domain.network.GetMainBannerUseCase
import com.example.domain.network.GetPlaceDetailUseCase
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DomainLayerNetworkPackageUnitTest {
    private val getMainBannerUseCase = mockk<GetMainBannerUseCase>()
    private val getGridItemUseCase = mockk<GetGridItemUseCase>()
    private val getPlaceDetailUseCase = mockk<GetPlaceDetailUseCase>()

    private val defaultLatLng = "0.0, 0.0"
    private val defaultPlaceID = "1234"

    @Before
    fun initUseCasesBehavior() {
        every { getMainBannerUseCase(defaultLatLng) } returns  flowOf(listOf())
        every { getGridItemUseCase(defaultLatLng) } returns flowOf(listOf())
        every { getPlaceDetailUseCase(defaultPlaceID) } returns flowOf(DomainLayerDummyData.DUMMY_DETAILED_PLACE_DATA)
    }

    @Test
    fun `execute should be return empty list from MainGridBannerUseCase`() = runBlocking {
        var result = listOf<BannerItemsModel>()

        getMainBannerUseCase(defaultLatLng).collect { result = it }

        assertEquals(
            listOf<BannerItemsModel>(),
            result
        )
    }

    @Test
    fun `execute should be return empty list from SubGridBannerUseCase`() = runBlocking {
        var result = listOf<GridItemsModel>()

        getGridItemUseCase(defaultLatLng).collect { result = it }

        assertEquals(
            listOf<GridItemsModel>(),
            result
        )
    }

    @Test
    fun `execute should be return Detail from Repository`() = runBlocking {
        var result: DetailedModel? = null

        getPlaceDetailUseCase(defaultPlaceID).collect { detailedInfo -> result = detailedInfo }

        assertEquals(
            DomainLayerDummyData.DUMMY_DETAILED_PLACE_DATA,
            result
        )
    }

    @After
    fun clearUseCasesMocking() {
        clearMocks(getMainBannerUseCase, getGridItemUseCase, getPlaceDetailUseCase)
    }
}