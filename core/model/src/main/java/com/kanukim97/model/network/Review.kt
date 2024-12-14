package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
    @SerialName("author_name") val authorName: String ? = "",
    @SerialName("author_url") val authorUrl: String? = "",
    @SerialName("language") val language: String? = "",
    @SerialName("original_language") val originalLanguage: String? = "",
    @SerialName("profile_photo_url") val profilePhotoUrl: String? = "",
    @SerialName("rating") val rating: Int? = 0,
    @SerialName("relative_time_description") val relativeTimeDescription: String? = "",
    @SerialName("text") val text: String? = "",
    @SerialName("time") val time: Int? = 0,
    @SerialName("translated") val translated: Boolean? = null
)