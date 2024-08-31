package com.example.domain

import com.example.domain.database.ReadAllCollectionUseCase
import com.example.domain.database.ReadCollectionUseCase
import com.example.domain.database.SaveCollectionUseCase
import com.example.domain.DomainLayerDummyData.DUMMY_COLLECTION_DATA
import com.example.model.domain.CollectionModel
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
        every { saveCollectionUseCase(DUMMY_COLLECTION_DATA[0]) } returns flowOf(Result.success(Unit))
        every { saveCollectionUseCase(DUMMY_COLLECTION_DATA[1]) } returns flowOf(Result.success(Unit))
        every { saveCollectionUseCase(DUMMY_COLLECTION_DATA[2]) } returns flowOf(Result.success(Unit))

        every { readCollectionUseCase("1") } returns flowOf(DUMMY_COLLECTION_DATA[0])
        every { readCollectionUseCase("2") } returns flowOf(DUMMY_COLLECTION_DATA[1])
        every { readCollectionUseCase("3") } returns flowOf(DUMMY_COLLECTION_DATA[2])

        every { readAllCollectionUseCase() } returns flowOf(DUMMY_COLLECTION_DATA)
    }

    @Test
    fun `test collection list index 0 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(DUMMY_COLLECTION_DATA[0]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `test collection list index 1 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(DUMMY_COLLECTION_DATA[1]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `test collection list index 2 should be return success from saveCollectionUseCase`() = runBlocking {
        var saveResult: Result<Unit>? = null

        saveCollectionUseCase(DUMMY_COLLECTION_DATA[2]).collect { result -> saveResult = result }
        assertEquals(saveResult, Result.success(Unit))
    }

    @Test
    fun `place id 1 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
        var data: CollectionModel? = null

        readCollectionUseCase("1").collect { result -> data = result }

        assertEquals(data, DUMMY_COLLECTION_DATA[0])
        assertEquals(data?.id, DUMMY_COLLECTION_DATA[0].id)
        assertEquals(data?.name, DUMMY_COLLECTION_DATA[0].name)
        assertEquals(data?.latLng, DUMMY_COLLECTION_DATA[0].latLng)
        assertEquals(data?.imgUrl, DUMMY_COLLECTION_DATA[0].imgUrl)
    }
    @Test
    fun `place id 2 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
        var data: CollectionModel? = null

        readCollectionUseCase("2").collect { result -> data = result }

        assertEquals(data, DUMMY_COLLECTION_DATA[1])
        assertEquals(data?.id, DUMMY_COLLECTION_DATA[1].id)
        assertEquals(data?.name, DUMMY_COLLECTION_DATA[1].name)
        assertEquals(data?.latLng, DUMMY_COLLECTION_DATA[1].latLng)
        assertEquals(data?.imgUrl, DUMMY_COLLECTION_DATA[1].imgUrl)
    }

    @Test
    fun `place id 3 should be return testCollection index 0 data from readCollectionUseCase`() = runBlocking {
            var data: CollectionModel? = null

            readCollectionUseCase("3").collect { result -> data = result }

            assertEquals(data, DUMMY_COLLECTION_DATA[2])
            assertEquals(data?.id, DUMMY_COLLECTION_DATA[2].id)
            assertEquals(data?.name, DUMMY_COLLECTION_DATA[2].name)
            assertEquals(data?.latLng, DUMMY_COLLECTION_DATA[2].latLng)
            assertEquals(data?.imgUrl, DUMMY_COLLECTION_DATA[2].imgUrl)
        }

    @Test
    fun `return all testCollection list data from readAllCollectionUseCase`() = runBlocking {
        var data: List<CollectionModel> = listOf()

        readAllCollectionUseCase().collect { result -> data = result }
        assertEquals(data, DUMMY_COLLECTION_DATA)
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