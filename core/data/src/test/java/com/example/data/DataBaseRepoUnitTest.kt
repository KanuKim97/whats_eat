package com.example.data

import com.example.data.repository.DatabaseRepository
import com.example.model.domain.CollectionModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataBaseRepoUnitTest {
    private val databaseRepositoryImpl = mockk<DatabaseRepository>()

    @Before
    fun initRepositoryFunctionsBehavior() {
        every { databaseRepositoryImpl.readAllCollectionEntities() } returns flowOf(DataLayerDummyData.DUMMY_COLLECTION_DATA)
        every { databaseRepositoryImpl.readCollectionEntity("2") } returns flowOf(DataLayerDummyData.DUMMY_COLLECTION_DATA[1])
    }


    @Test
    fun `execute should return readAllCollectionEntities`() = runTest {
        var result = listOf<CollectionModel>()

        databaseRepositoryImpl
            .readAllCollectionEntities()
            .collect { transactionResult -> result = transactionResult }

        assertEquals(
            DataLayerDummyData.DUMMY_COLLECTION_DATA,
            result
        )
    }

    @Test
    fun `execute readCollectionEntity should return data successful`() = runTest {
        databaseRepositoryImpl
            .readCollectionEntity("2")
            .collectLatest { assertEquals(DataLayerDummyData.DUMMY_COLLECTION_DATA[1], it) }
    }


    @After
    fun `clear Mocking Repository`() {
        clearMocks(databaseRepositoryImpl)
    }
}