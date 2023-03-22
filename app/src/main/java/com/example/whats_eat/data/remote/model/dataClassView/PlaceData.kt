package com.example.whats_eat.data.remote.model.dataClassView

import com.google.gson.annotations.SerializedName

data class PlaceData(
    @SerializedName("PlaceName")
    val placeName: String,
    @SerializedName("PlaceAddress")
    val placeAddress: String,
    @SerializedName("RatingNumber")
    val ratingNumber: Float,
    @SerializedName("PlacePhotoUrl")
    val placePhotoRef: String,
    @SerializedName("PlaceKey")
    val key: String
)
