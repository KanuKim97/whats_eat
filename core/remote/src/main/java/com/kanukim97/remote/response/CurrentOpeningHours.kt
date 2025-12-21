package com.kanukim97.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentOpeningHours(
    @SerialName("open_now") val openNow: Boolean? = null,
    @SerialName("periods") val periods: List<Period>? = emptyList(),
    @SerialName("weekday_text") val weekdayText: List<String>? = emptyList()
)