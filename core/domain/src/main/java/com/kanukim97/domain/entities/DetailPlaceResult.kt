package com.kanukim97.domain.entities

data class DetailPlaceResult(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val address: String,
    val phoneNumber: String,
    val rating: Double?,
    val reviewCount: Int?,
    val latitude: Double,
    val longitude: Double,
    val isOpened: Boolean?,
    val url: String,
    val reviews: List<Review>? = emptyList()
)

data class Review(
    val authorName: String,
    val profilePhotoUrl: String?,
    val rating: Int?,
    val content: String?
)