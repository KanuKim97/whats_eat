package com.kanukim97.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kanukim97.database.dao.EatDao
import com.kanukim97.database.DataBaseLayerDummyData.DUMMY_ENTITIES_LIST
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class DataBaseUnitTest {
    private lateinit var eatDAO: EatDao
    private lateinit var eatDataBase: EatDataBase

    @Before
    fun createDataBase() {
        val context: Context = ApplicationProvider.getApplicationContext()

        eatDataBase = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = EatDataBase::class.java
        ).build()

        eatDAO = eatDataBase.eatDao()
    }

    @Test
    @Throws(Exception::class)
    fun insert_collection_into_database() = runTest {
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[0])
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[1])
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[2])

        val result = eatDAO.readCollection(DUMMY_ENTITIES_LIST[0].id).first()

        assertEquals(DUMMY_ENTITIES_LIST[0], result)
    }

    @Test
    @Throws(Exception::class)
    fun delete_collection_into_database() = runTest {
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[0])
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[1])
        eatDAO.saveCollection(DUMMY_ENTITIES_LIST[2])

        eatDAO.deleteCollectionById(DUMMY_ENTITIES_LIST[0])

        val result = eatDAO.readAllCollections().first()

        assertEquals(listOf(DUMMY_ENTITIES_LIST[1], DUMMY_ENTITIES_LIST[2]), result)
    }

    @After
    fun closeDataBase() {
        eatDataBase.close()
    }
}