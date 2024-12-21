package com.kanukim97.model.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlusCode(
    @SerialName("compound_code") val compoundCode: String? = "",
    @SerialName("global_code") val globalCode: String? = ""
)