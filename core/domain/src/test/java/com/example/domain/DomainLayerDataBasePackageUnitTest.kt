package com.example.domain

import com.example.domain.database.ReadAllCollectionUseCase
import com.example.domain.database.ReadCollectionUseCase
import com.example.domain.database.SaveCollectionUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

import org.junit.Before

class DomainLayerDataBasePackageUnitTest {
    private val readAllCollectionUseCase = mockk<ReadAllCollectionUseCase>()
    private val readCollectionUseCase = mockk<ReadCollectionUseCase>()
    private val saveCollectionUseCase = mockk<SaveCollectionUseCase>()

    @Before
    fun initUseCases() {
        every { readAllCollectionUseCase() } returns flowOf(listOf())
    }

    @Test
    fun addition_isCorrect() {
    }
}