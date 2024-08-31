package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Photo(
    @SerialName("height") val height: Int? = 0,
    @SerialName("width") val width: Int? = 0,
    @SerialName("html_attributions") val htmlAttributions: List<String>? = emptyList(),
    @SerialName("photo_reference") val photoReference: String = ""
) {
    fun getFullPhotoReference(apiKey: String): String {
        return StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
            .append("?maxwidth=1000")
            .append("&photo_reference=${this.photoReference}")
            .append("&key=${apiKey}")
            .toString()
    }
}