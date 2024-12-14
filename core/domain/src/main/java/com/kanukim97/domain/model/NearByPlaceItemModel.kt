package com.kanukim97.domain.model

data class NearByPlaceItemModel(
    val placeId: String,
    val placeName: String,
    val placePhotoReference: String = ""
)