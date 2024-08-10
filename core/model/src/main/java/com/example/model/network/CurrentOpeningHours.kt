package com.example.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentOpeningHours(
    @SerialName("open_now") val openNow: Boolean,
    @SerialName("periods") val periods: List<Period>,
    @SerialName("weekday_text") val weekdayText: List<String>
)