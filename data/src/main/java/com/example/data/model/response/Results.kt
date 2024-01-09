package com.example.data.model.response

import com.example.data.model.response.responseModel.loaction.Geometry
import com.example.data.model.response.responseModel.period.OpeningHours
import com.example.data.model.response.responseModel.photo.Photos

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