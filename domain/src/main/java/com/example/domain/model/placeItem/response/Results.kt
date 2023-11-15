package com.example.domain.model.placeItem.response

data class Results(
    val name: String?,
    val id: String?,
    val place_id: String?,
    val geometry: Geometry?,
    val formatted_address: String?,
    val rating: Double?,
    val openingHours: OpeningHours?,
    val photos: ArrayList<Photos>?,
    val url: String?
)

data class Geometry(val location: Location)

data class Location(val lat: Double, val lng: Double)

data class OpeningHours(val open_now: Boolean)

data class Photos(val height: Int, val width: Int, val photo_reference: String?)