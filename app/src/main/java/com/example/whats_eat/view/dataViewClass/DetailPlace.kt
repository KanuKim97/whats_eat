package com.example.whats_eat.view.dataViewClass

data class DetailPlace(
    val name: String?,
    val formattedAddress: String?,
    val isOpenNow: Boolean?,
    val rating: Double?,
    val lat: Double?,
    val lng: Double?,
    val photoRef: String
)
