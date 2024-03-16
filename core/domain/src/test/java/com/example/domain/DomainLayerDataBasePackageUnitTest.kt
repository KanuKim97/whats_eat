package com.example.domain

import com.example.domain.database.ReadAllCollectionUseCase
import com.example.domain.database.ReadCollectionUseCase
import com.example.domain.database.SaveCollectionUseCase
import com.example.domain.testData.testCollectionModelList
import com.example.model.collection.CollectionModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

import org.junit.Before

class DomainLayerDataBasePackageUnitTest {
    private val readAllCollectionUseCase = mockk<ReadAllCollectionUseCase>()
    private val readCollectionUseCase = mockk<ReadCollectionUseCase>()
    private val saveCollectionUseCase = mockk<SaveCollectionUseCase>()

    @Before
    fun init_UseCases() {
        every { saveCollectionUseCase(testCollectionModelList[0]) } returns flowOf(Result.success(Unit))
        every { saveCollectionUseCase(testCollectionModelList[1]) } returns flowOf(Result.success(Unit))
        every { saveCollectionUseCase(testCollectionModelList[2]) } returns flowOf(Result.success(Unit))

        every { readCollectionUseCase("1") } returns flowOf(testCollectionModelList[0])
        every { readCollectionUseCase("2") } returns flowOf(testCollectionModelList[1])
        every { readCollectionUseCase("3") } returns flowOf(testCollectionModelList[2])

        every { readAllCollectionUseCase() } returns flowOf(testCollectionModelList)
    }

    @Test
    fun `test collection list index 0 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(testCollectionModelList[0]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `test collection list index 1 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(testCollectionModelList[1]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `test collection list index 2 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(testCollectionModelList[2]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `place id 1 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
        var data: CollectionModel? = null

        readCollectionUseCase("1").collect { result -> data = result }

        assertEquals(data, testCollectionModelList[0])
        assertEquals(data?.id, testCollectionModelList[0].id)
        assertEquals(data?.name, testCollectionModelList[0].name)
        assertEquals(data?.latLng, testCollectionModelList[0].latLng)
        assertEquals(data?.imgUrl, testCollectionModelList[0].imgUrl)
    }
    @Test
    fun `place id 2 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
        var data: CollectionModel? = null

        readCollectionUseCase("2").collect { result -> data = result }

        assertEquals(data, testCollectionModelList[1])
        assertEquals(data?.id, testCollectionModelList[1].id)
        assertEquals(data?.name, testCollectionModelList[1].name)
        assertEquals(data?.latLng, testCollectionModelList[1].latLng)
        assertEquals(data?.imgUrl, testCollectionModelList[1].imgUrl)
    }

    @Test
    fun `place id 3 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
            var data: CollectionModel? = null

            readCollectionUseCase("3").collect { result -> data = result }

            assertEquals(data, testCollectionModelList[2])
            assertEquals(data?.id, testCollectionModelList[2].id)
            assertEquals(data?.name, testCollectionModelList[2].name)
            assertEquals(data?.latLng, testCollectionModelList[2].latLng)
            assertEquals(data?.imgUrl, testCollectionModelList[2].imgUrl)
        }

    @Test
    fun `return all testCollection list data from readAllCollectionUseCase`() = runBlocking {
        var data: List<CollectionModel> = listOf()

        readAllCollectionUseCase().collect { result -> data = result }
        assertEquals(data, testCollectionModelList)
    }

    @After
    fun clear_UseCases_Mocking() {
        clearMocks(
            readAllCollectionUseCase,
            readCollectionUseCase,
            saveCollectionUseCase
        )
    }
}