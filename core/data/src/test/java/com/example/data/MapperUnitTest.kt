package com.example.data

import com.example.data.mapper.entityToDataModelMapper
import com.example.data.mapper.dataModelToEntityMapper
import com.example.data.model.CollectionDataModel
import com.example.database.model.CollectionEntity
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
    private val collectionModel: CollectionDataModel =
        CollectionDataModel(
            id = "dummy",
            name = "dummy",
            latLng = "dummy",
            imgUrl = "dummy"
        )


    @Test
    fun mapping_ENTITY_TO_MODEL() {
        val mappingResult = entityToDataModelMapper(collectionEntity)
        assertEquals(collectionModel, mappingResult)
    }

    @Test
    fun mapping_MODEL_TO_ENTITY() {
        val mappingResult = dataModelToEntityMapper(collectionModel)
        assertEquals(collectionEntity, mappingResult)
    }
}