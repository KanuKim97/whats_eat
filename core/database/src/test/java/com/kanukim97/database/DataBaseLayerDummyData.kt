package com.kanukim97.database

import com.kanukim97.database.model.CollectionEntity

internal object DataBaseLayerDummyData {
    val DUMMY_ENTITIES_LIST = listOf(
        CollectionEntity(
            id = "1",
            name = "default",
            latLng = "default",
            imageUrl = "default"
        ),
        CollectionEntity(
            id = "2",
            name = "default",
            latLng = "default",
            imageUrl = "default"
        ),
        CollectionEntity(
            id = "3",
            name = "default",
            latLng = "default",
            imageUrl = "default"
        )
    )
}