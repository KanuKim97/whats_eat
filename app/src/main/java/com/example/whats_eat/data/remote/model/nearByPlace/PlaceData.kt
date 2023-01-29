package com.example.whats_eat.data.remote.model.nearByPlace

import com.google.gson.annotations.SerializedName

data class PlaceData(

    @SerializedName("placeName")
    var placeName: String = "",

    @SerializedName("placeAddress")
    var placeAddress: String = "",

    @SerializedName("ratingNum")
    var ratingNum: Float = 0.0f,

    @SerializedName("placePhotoUrl")
    var placePhotoUrl: String = "",

    @SerializedName("key")
    var key: String = ""
    )