package com.kanukim97.database

import com.kanukim97.database.model.CollectionEntity

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