package com.example.whats_eat.data.model.nearByPlace

import com.google.gson.annotations.SerializedName

data class PlaceData(

    @SerializedName("placeName")
    var placeName: String,

    @SerializedName("placeAddress")
    var placeAddress: String,

    @SerializedName("ratingNum")
    var ratingNum: Float,

    @SerializedName("placePhotoUrl")
    var placePhotoUrl: String
    )
