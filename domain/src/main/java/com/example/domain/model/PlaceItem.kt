package com.example.domain.model

data class PlaceItem(
    val name: String?,
    val id: String?,
    val place_id: String?,
    val lat: Double,
    val lng: Double,
    val formatted_address: String?,
    val rating: Double?,
    val open_now: Boolean,
    val photos: ArrayList<Photos>?,
    val url: String?
)

data class Photos(
    val height: Int,
    val width: Int,
    val photo_reference: String?
)