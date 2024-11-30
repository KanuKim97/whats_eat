package com.example.domain.model

data class NearByPlaceItemModel(
    val placeId: String,
    val placeName: String,
    val placePhotoReference: String = ""
)