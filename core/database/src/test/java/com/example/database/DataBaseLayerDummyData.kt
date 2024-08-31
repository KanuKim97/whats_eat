package com.example.database

import com.example.database.model.CollectionEntity

internal object DataBaseLayerDummyData {
    val DUMMY_ENTITIES_LIST = listOf(
        CollectionEntity(
            placeID = "1",
            placeName = "default",
            placeLatLng = "default",
            placeImgUrl = "default"
        ),
        CollectionEntity(
            placeID = "2",
            placeName = "default",
            placeLatLng = "default",
            placeImgUrl = "default"
        ),
        CollectionEntity(
            placeID = "3",
            placeName = "default",
            placeLatLng = "default",
            placeImgUrl = "default"
        )
    )
}