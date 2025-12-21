package com.kanukim97.domain.entities

data class Place(
    val id: String,
    val name: String,
    val rating: Double?,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String?,
)
