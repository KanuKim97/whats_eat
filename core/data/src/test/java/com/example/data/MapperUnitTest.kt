package com.example.data

import com.example.data.util.entityToModelMapper
import com.example.data.util.modelToEntityMapper
import com.example.database.model.CollectionEntity
import com.example.model.feature.CollectionModel
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Mapper local unit test
 */
class MapperUnitTest {
    private val collectionEntity: CollectionEntity = CollectionEntity(
        placeID = "dummy",
        placeName = "dummy",
        placeLatLng = "dummy",
        placeImgUrl = "dummy"
    )
    private val collectionModel: CollectionModel = CollectionModel(
        id = "dummy",
        name = "dummy",
        latLng = "dummy",
        imgUrl = "dummy"
    )


    @Test
    fun mapping_ENTITY_TO_MODEL() {
        val mappingResult = entityToModelMapper(collectionEntity)
        assertEquals(collectionModel, mappingResult)
    }

    @Test
    fun mapping_MODEL_TO_ENTITY() {
        val mappingResult = modelToEntityMapper(collectionModel)
        assertEquals(collectionEntity, mappingResult)
    }
}