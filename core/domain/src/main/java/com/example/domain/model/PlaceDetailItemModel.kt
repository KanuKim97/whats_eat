package com.example.domain.model

data class PlaceDetailItemModel(
    val placeId: String,
    val placeName: String,
    val placeRating: String,
    val placeImgUrl: String,
    val placeAddress: String,
    val placePhoneNumber: String,
    val placeLatitude: Double,
    val placeLongitude: Double,
    val isPlaceOpenNow: Boolean
)