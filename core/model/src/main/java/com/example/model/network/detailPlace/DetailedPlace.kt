package com.example.model.network.detailPlace

import com.example.model.network.AddressComponent
import com.example.model.network.CurrentOpeningHours
import com.example.model.network.Geometry
import com.example.model.network.OpeningHours
import com.example.model.network.Photo
import com.example.model.network.PlusCode
import com.example.model.network.Review
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailedPlace(
    @SerialName("status") val status : String,
    @SerialName("result") val result : DetailedResult
)

@Serializable
data class DetailedResult(
    @SerialName("address_components") val addressComponents: List<AddressComponent> = listOf(),
    @SerialName("adr_address") val adrAddress: String,
    @SerialName("business_status") val businessStatus: String,
    @SerialName("current_opening_hours") val currentOpeningHours: CurrentOpeningHours,
    @SerialName("delivery") val delivery: Boolean,
    @SerialName("dine_ni") val dineIn: Boolean,
    @SerialName("formatted_address") val formattedAddress: String,
    @SerialName("formatted_phone_number") val formattedPhoneNumber: String,
    @SerialName("geometry") val geometry: Geometry,
    @SerialName("icon") val icon: String,
    @SerialName("icon_background_color") val iconBackgroundColor: String,
    @SerialName("icon_mask_base_uri") val iconMaskBaseUri: String,
    @SerialName("international_phone_number") val internationalPhoneNumber: String,
    @SerialName("name") val name: String,
    @SerialName("opening_hours") val openingHours: OpeningHours,
    @SerialName("photos") val photos: List<Photo>,
    @SerialName("place_id") val placeId: String,
    @SerialName("plus_code") val plusCode: PlusCode,
    @SerialName("rating") val rating: Double,
    @SerialName("reference") val reference: String,
    @SerialName("reservable") val reservable: Boolean,
    @SerialName("reviews") val reviews: List<Review>,
    @SerialName("serves_beer") val servesBeer: Boolean,
    @SerialName("serves_dinner") val servesDinner: Boolean,
    @SerialName("serves_lunch") val servesLunch: Boolean,
    @SerialName("serves_wine") val servesWine: Boolean,
    @SerialName("types") val types: List<String>,
    @SerialName("url") val url: String,
    @SerialName("user_ratings_total") val userRatingsTotal: Int,
    @SerialName("utc_offset") val utcOffset: Int,
    @SerialName("vicinity") val vicinity: String,
    @SerialName("website") val website: String
)