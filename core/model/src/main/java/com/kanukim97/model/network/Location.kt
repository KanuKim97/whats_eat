package com.kanukim97.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("lat") val lat: Double? = 0.0,
    @SerialName("lng") val lng: Double? = 0.0
)