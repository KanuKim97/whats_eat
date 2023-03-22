package com.example.whats_eat.data.remote.model.dataClassView

import com.google.gson.annotations.SerializedName

data class ViewPlaceModel(
    @SerializedName("PlaceName")
    val placeName: String,
    @SerializedName("PlaceAddress")
    val placeAddress: String,
    @SerializedName("RatingNumber")
    val ratingNum: Float,
    @SerializedName("PlacePhotoUrl")
    val placePhotoUrl: String
)
