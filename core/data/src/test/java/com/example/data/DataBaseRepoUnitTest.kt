package com.example.data

import com.example.data.repository.DatabaseRepository
import com.example.data.sampleDBdata.sampleCollectionData
import com.example.model.collection.CollectionModel
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
    private val dbRepoImpl = mockk<DatabaseRepository>()

    @Before
    fun init_RepoImpl() {
        every { dbRepoImpl.saveUserCollection(sampleCollectionData[0]) } returns flowOf(Result.success(Unit))
        every { dbRepoImpl.saveUserCollection(sampleCollectionData[1]) } returns flowOf(Result.success(Unit))
        every { dbRepoImpl.saveUserCollection(sampleCollectionData[2]) } returns flowOf(Result.success(Unit))

        every { dbRepoImpl.readAllCollectionEntities() } returns flowOf(sampleCollectionData)

        every { dbRepoImpl.readCollectionEntity("1") } returns flowOf(sampleCollectionData[0])
        every { dbRepoImpl.readCollectionEntity("2") } returns flowOf(sampleCollectionData[1])
        every { dbRepoImpl.readCollectionEntity("3") } returns flowOf(sampleCollectionData[2])

        every { dbRepoImpl.deleteUserCollection(sampleCollectionData[1]) } returns flowOf(Result.success(Unit))
    }

    @Test
    fun `read data should be return CollectionModel List from dbRepoImpl`() = runBlocking {
        var result = listOf<CollectionModel>()

        dbRepoImpl.readAllCollectionEntities().collect { list -> result = list }
        assertEquals(result, sampleCollectionData)
    }

    @Test
    fun `read data should be return CollectionModel from dbRepoImpl`() = runBlocking {
        var result: CollectionModel? = null

        dbRepoImpl.readCollectionEntity("1").collect { data -> result = data }
        assertEquals(result, sampleCollectionData[0])
    }

    @Test
    fun `delete data should be return success from dbRepoImpl`() = runBlocking {
        var result: Result<Unit>? = null

        dbRepoImpl
            .deleteUserCollection(sampleCollectionData[1])
            .collect { isSuccess -> result = isSuccess }
        assertEquals(result, Result.success(Unit))
    }


    @After
    fun clear_dbRepoImpl_Mocking() {
        clearMocks(dbRepoImpl)
    }
}