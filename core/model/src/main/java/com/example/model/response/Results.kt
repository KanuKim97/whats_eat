package com.example.model.response

import com.example.model.response.location.Geometry
import com.example.model.response.period.OpeningHours
import com.example.model.response.photo.Photos

data class Results(
    val name: String? = null,
    val id: String? = null,
    val place_id: String? = null,
    val geometry: Geometry? = null,
    val formatted_address: String? = null,
    val rating: Double? = null,
    val openingHours: OpeningHours? = null,
    val photos: ArrayList<Photos>? = arrayListOf(),
    val url: String? = null
)