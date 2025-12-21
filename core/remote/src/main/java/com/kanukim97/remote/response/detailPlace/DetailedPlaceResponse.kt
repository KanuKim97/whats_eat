package com.kanukim97.remote.response.detailPlace

import com.kanukim97.remote.response.AddressComponent
import com.kanukim97.remote.response.CurrentOpeningHours
import com.kanukim97.remote.response.Geometry
import com.kanukim97.remote.response.OpeningHours
import com.kanukim97.remote.response.Photo
import com.kanukim97.remote.response.PlusCode
import com.kanukim97.remote.response.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedPlaceResponse(
    @SerialName("status") val status : String,
    @SerialName("result") val result : DetailedResultResponse? = null
)

@Serializable
data class DetailedResultResponse(
    @SerialName("address_components") val addressComponents: List<AddressComponent>? = emptyList(),
    @SerialName("adr_address") val adrAddress: String ? = "",
    @SerialName("business_status") val businessStatus: String? = "",
    @SerialName("current_opening_hours") val currentOpeningHours: CurrentOpeningHours? = null,
    @SerialName("delivery") val delivery: Boolean? = null,
    @SerialName("dine_ni") val dineIn: Boolean? = null,
    @SerialName("formatted_address") val formattedAddress: String? = "",
    @SerialName("formatted_phone_number") val formattedPhoneNumber: String? = "",
    @SerialName("geometry") val geometry: Geometry? = null,
    @SerialName("icon") val icon: String? = "",
    @SerialName("icon_background_color") val iconBackgroundColor: String? = "",
    @SerialName("icon_mask_base_uri") val iconMaskBaseUri: String? = "",
    @SerialName("international_phone_number") val internationalPhoneNumber: String? = "",
    @SerialName("name") val name: String? = "",
    @SerialName("opening_hours") val openingHours: OpeningHours? = null,
    @SerialName("photos") val photos: List<Photo>? = emptyList(),
    @SerialName("place_id") val placeId: String? = "",
    @SerialName("plus_code") val plusCode: PlusCode? = null,
    @SerialName("rating") val rating: Double? = null,
    @SerialName("reference") val reference: String? = "",
    @SerialName("reservable") val reservable: Boolean? = null,
    @SerialName("reviews") val reviews: List<Review>? = emptyList(),
    @SerialName("serves_beer") val servesBeer: Boolean? = null,
    @SerialName("serves_dinner") val servesDinner: Boolean? = null,
    @SerialName("serves_lunch") val servesLunch: Boolean? = null,
    @SerialName("serves_wine") val servesWine: Boolean? = null,
    @SerialName("types") val types: List<String>? = emptyList(),
    @SerialName("url") val url: String? = "",
    @SerialName("user_ratings_total") val userRatingsTotal: Int? = null,
    @SerialName("utc_offset") val utcOffset: Int? = null,
    @SerialName("vicinity") val vicinity: String? = "",
    @SerialName("website") val website: String? = ""
)