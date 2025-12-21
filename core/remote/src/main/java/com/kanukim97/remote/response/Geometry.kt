package com.kanukim97.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    @SerialName("location") val location: Location? = null,
    @SerialName("viewport") val viewport: Viewport? = null
)