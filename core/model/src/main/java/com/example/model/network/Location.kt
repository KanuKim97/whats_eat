package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Location(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double
)