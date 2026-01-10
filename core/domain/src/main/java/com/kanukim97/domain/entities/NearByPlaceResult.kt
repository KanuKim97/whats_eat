package com.kanukim97.domain.entities


data class NearByPlaceResult(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val rating: Double?,
    val latitude: Double,
    val longitude: Double,
    val ref: String?,
    val vicinity: String?
)