package com.example.whats_eat.presenter.ViewModelItems

data class DetailPlace(
    val name: String? = null,
    val formattedAddress: String? = null,
    val isOpenNow: Boolean? = null,
    val rating: Double? = null,
    val lat: Double? = null,
    val lng: Double? = null,
    val photoRef: String = ""
)
