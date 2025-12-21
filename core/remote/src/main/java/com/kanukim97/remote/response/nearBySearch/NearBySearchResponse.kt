package com.kanukim97.remote.response.nearBySearch

import com.kanukim97.remote.response.Geometry
import com.kanukim97.remote.response.OpeningHours
import com.kanukim97.remote.response.Photo
import com.kanukim97.remote.response.PlusCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NearBySearchResponse(
    @SerialName("status") val status: String,
    @SerialName("results") val results: List<NearBySearchResultResponse> = emptyList()
)

@Serializable
data class NearBySearchResultResponse(
    @SerialName("business_status") val businessStatus: String? = "",
    @SerialName("geometry") val geometry: Geometry? = null,
    @SerialName("icon") val icon: String? = "",
    @SerialName("icon_background_color") val iconBackgroundColor: String? = "",
    @SerialName("icon_mask_base_uri") val iconMaskBaseUri: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("opening_hours") val openingHours: OpeningHours? = null,
    @SerialName("photos") val photos: List<Photo> = emptyList(),
    @SerialName("place_id") val placeId: String? = "",
    @SerialName("plus_code") val plusCode: PlusCode? = null,
    @SerialName("price_level") val priceLevel: Int? = null,
    @SerialName("rating") val rating: Double? = 0.0,
    @SerialName("reference") val reference: String? = "",
    @SerialName("scope") val scope: String? = "",
    @SerialName("types") val types: List<String>? = emptyList(),
    @SerialName("user_ratings_total") val userRatingsTotal: Int? = 0,
    @SerialName("vicinity") val vicinity: String? = ""
)