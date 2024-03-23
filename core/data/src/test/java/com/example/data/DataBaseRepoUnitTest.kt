package com.example.data

import com.example.data.repository.DatabaseRepository
import com.example.data.sampleDBdata.sampleCollectionData
import com.example.model.collection.CollectionModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
        every { databaseRepositoryImpl.readAllCollectionEntities() } returns flowOf(listOf())
    }

    @Test
    fun `execute should return readAllCollectionEntities`() = runBlocking {
        var result = listOf<CollectionModel>()

        databaseRepositoryImpl
            .readAllCollectionEntities()
            .collect { transactionResult -> result = transactionResult }

        assertEquals(
            sampleCollectionData,
            result
        )
    }
}