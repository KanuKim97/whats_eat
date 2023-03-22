package com.example.whats_eat.data.remote.model.responseModel

import com.example.whats_eat.data.remote.model.responseModel.modelContent.loaction.Geometry
import com.example.whats_eat.data.remote.model.responseModel.modelContent.period.OpeningHours
import com.example.whats_eat.data.remote.model.responseModel.modelContent.photo.Photos

data class Results(
    val name: String,
    val geometry: Geometry,
    val photos: Array<Photos>,
    val id: String,
    val place_id: String,
    val rating: Double,
    val opening_hours: OpeningHours,
    val url: String,
    val formatted_address: String
)