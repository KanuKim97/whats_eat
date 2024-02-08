package com.example.model.details

import com.example.model.network.AddressComponent
import com.example.model.network.CurrentOpeningHours
import com.example.model.network.Geometry
import com.example.model.network.OpeningHours
import com.example.model.network.Photo
import com.example.model.network.PlusCode
import com.example.model.network.Review

data class DetailedResult(
    val address_components: List<AddressComponent>,
    val adr_address: String,
    val business_status: String,
    val current_opening_hours: CurrentOpeningHours,
    val delivery: Boolean,
    val dine_in: Boolean,
    val formatted_address: String,
    val formatted_phone_number: String,
    val geometry: Geometry,
    val icon: String,
    val icon_background_color: String,
    val icon_mask_base_uri: String,
    val international_phone_number: String,
    val name: String,
    val opening_hours: OpeningHours,
    val photos: List<Photo>,
    val place_id: String,
    val plus_code: PlusCode,
    val rating: Double,
    val reference: String,
    val reservable: Boolean,
    val reviews: List<Review>,
    val serves_beer: Boolean,
    val serves_dinner: Boolean,
    val serves_lunch: Boolean,
    val serves_wine: Boolean,
    val types: List<String>,
    val url: String,
    val user_ratings_total: Int,
    val utc_offset: Int,
    val vicinity: String,
    val website: String
)