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
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double
)

@Serializable
data class Northeast(
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double
)