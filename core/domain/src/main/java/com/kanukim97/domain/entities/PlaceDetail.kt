package com.kanukim97.domain.entities

data class PlaceDetail(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val rating: String,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val isOpenNow: Boolean?,
    val url: String
)