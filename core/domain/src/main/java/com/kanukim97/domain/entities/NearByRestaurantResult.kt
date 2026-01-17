package com.kanukim97.domain.entities

data class NearByRestaurantResult(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val rating: Double?,
    val latitude: Double,
    val longitude: Double,
    val reviewCount: Int?
)