package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Period(
    @SerialName("open") val open: Open,
    @SerialName("close") val close: Close
)

@Serializable
data class Open(
    @SerialName("date") val date: String? = "",
    @SerialName("day") val day: Int? = 0,
    @SerialName("time") val time: String? = ""
)

@Serializable
data class Close(
    @SerialName("date") val date: String? = "",
    @SerialName("day") val day: Int? = 0,
    @SerialName("time") val time: String? = ""
)