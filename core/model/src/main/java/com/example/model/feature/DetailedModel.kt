package com.example.model.feature

data class DetailedModel(
    val placeId: String,
    val placeName: String,
    val placeImgUrl: String,
    val placeLatitude: Double,
    val placeLongitude: Double,
    val isPlaceOpenNow: Boolean
)