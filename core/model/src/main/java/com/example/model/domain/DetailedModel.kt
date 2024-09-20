package com.example.model.domain

data class DetailedModel(
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


data class DetailedDomainModel(
    val placeId: String,
    val placeName: String,
    val placeRating: String,
    val placeImgUrl: List<String> = emptyList(),
    val placeAddress: String,
    val placePhoneNumber: String,
    val placeLatitude: Double,
    val placeLongitude: Double,
    val isPlaceOpenNow: Boolean
)