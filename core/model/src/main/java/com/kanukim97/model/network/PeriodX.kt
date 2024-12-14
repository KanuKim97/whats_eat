package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PeriodX(
    @SerialName("open") val open: OpenX? = null,
    @SerialName("close") val close: CloseX? = null
)

@Serializable
data class OpenX(
    @SerialName("day") val day: Int? = 0,
    @SerialName("time") val time: String? = ""
)

@Serializable
data class CloseX(
    @SerialName("day") val day: Int? = 0,
    @SerialName("time") val time: String? = ""
)