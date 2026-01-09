package com.kanukim97.data.model

data class DetailPlaceResult(
    val id: String,
    val name: String,
    val imageUrls: List<String>,
    val address: String,
    val phoneNumber: String,
    val rating: Double?,
    val latitude: Double,
    val longitude: Double,
    val isOpened: Boolean?,
    val url: String
)