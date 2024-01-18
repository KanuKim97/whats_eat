package com.example.model.response

import com.example.model.response.location.Geometry
import com.example.model.response.period.OpeningHours
import com.example.model.response.photo.Photo
import com.example.model.response.plusCode.PlusCode

data class Results(
    val business_status: String,
    val geometry: Geometry,
    val icon: String,
    val icon_background_color: String,
    val icon_mask_base_uri: String,
    val name: String,
    val opening_hours: OpeningHours,
    val photos: List<Photo>,
    val place_id: String,
    val plus_code: PlusCode,
    val price_level: Int,
    val rating: Double,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val user_ratings_total: Int,
    val vicinity: String
)