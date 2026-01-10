package com.kanukim97.domain.entities

data class PlaceCollection(
    val id: String,
    val name: String,
    val latLng: String,
    val imageUrl: String = ""
)