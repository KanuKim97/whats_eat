package com.example.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.network.GetGridItemUseCase
import com.example.domain.network.GetMainBannerUseCase
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before

import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeViewModelInstrumentTest {
    val mainBannerUseCaseMock = mockk<GetMainBannerUseCase>()
    val gridItemUseCaseMock = mockk<GetGridItemUseCase>()

    private lateinit var homeViewModelMock: HomeViewModel

    @Before
    fun initMock() {
        every { mainBannerUseCaseMock("0.0, 0.0") } returns flowOf(listOf())
        every { gridItemUseCaseMock("0.0, 0.0") } returns flowOf(listOf())

        homeViewModelMock = HomeViewModel(mainBannerUseCaseMock, gridItemUseCaseMock)
    }

    @After
    fun clearMock() {
        clearMocks(mainBannerUseCaseMock, gridItemUseCaseMock)
    }
}