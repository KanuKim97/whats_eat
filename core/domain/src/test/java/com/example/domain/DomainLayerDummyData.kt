package com.example.domain

import com.example.model.domain.CollectionModel
import com.example.model.domain.DetailedModel

internal object DomainLayerDummyData {
    val DUMMY_COLLECTION_DATA = listOf(
        CollectionModel(
            id = "1",
            name = "Test Collection 1",
            latLng = "1.0, 1.0",
            imgUrl = "Test Collection Image Url 1"
        ),
        CollectionModel(
            id = "2",
            name = "Test Collection 2",
            latLng = "2.0, 2.0",
            imgUrl = "Test Collection Image Url 2"
        ),
        CollectionModel(
            id = "3",
            name = "Test Collection 3",
            latLng = "3.0, 3.0",
            imgUrl = "Test Collection Image Url 3"
        )
    )

    val DUMMY_DETAILED_PLACE_DATA = DetailedModel(
        placeId = "",
        placeName = "xx네 분식",
        placeRating = "4.9",
        placeImgUrl = "",
        placeAddress = "XXX XX XXX xxx-xxxx",
        placePhoneNumber = "xxx-xxxx-xxxx",
        placeLatitude = 0.0,
        placeLongitude = 0.0,
        isPlaceOpenNow = false
    )
}