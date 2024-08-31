package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Viewport(
    @SerialName("northeast") val northeast: Northeast,
    @SerialName("southwest") val southwest: Southwest
)

@Serializable
data class Southwest(
    @SerialName("lat") val lat: Double? = 0.0,
    @SerialName("lng") val lng: Double? = 0.0
)

@Serializable
data class Northeast(
    @SerialName("lat") val lat: Double? = 0.0,
    @SerialName("lng") val lng: Double? = 0.0
)