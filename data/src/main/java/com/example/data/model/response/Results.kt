package com.example.data.model.response

import com.example.whats_eat.data.remote.model.response.content.loaction.Geometry
import com.example.whats_eat.data.remote.model.response.content.period.OpeningHours
import com.example.whats_eat.data.remote.model.response.content.photo.Photos

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