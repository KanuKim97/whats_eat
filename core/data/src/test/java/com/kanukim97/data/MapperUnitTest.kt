package com.kanukim97.data

import com.kanukim97.data.mapper.entityToDataModelMapper
import com.kanukim97.data.mapper.dataModelToEntityMapper
import com.kanukim97.data.model.CollectionDataModel
import com.kanukim97.database.model.CollectionEntity
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