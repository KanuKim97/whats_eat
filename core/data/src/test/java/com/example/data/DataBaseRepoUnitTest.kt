package com.example.data

import com.example.data.repository.DatabaseRepositoryImpl
import com.example.data.sampleDBdata.sampleCollectionData
import com.example.database.dao.EatDao
import com.example.model.collection.CollectionModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DataBaseRepoUnitTest {
    @Mock
    private lateinit var dao: EatDao
    private lateinit var databaseRepositoryImpl: DatabaseRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        databaseRepositoryImpl = DatabaseRepositoryImpl(dao, testDispatcher)

        `when`(databaseRepositoryImpl.readAllCollectionEntities()).thenReturn(
            flowOf(sampleCollectionData)
        )
    }

    @Test
    fun `execute should return readAllCollectionEntities`(): Unit = runBlocking {
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