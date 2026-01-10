package com.kanukim97.domain.entities

data class PlaceDetail(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val rating: String,
    val reviewsCount: Int?,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val isOpenNow: Boolean?,
    val url: String,
    val review: List<Review> = emptyList()
)