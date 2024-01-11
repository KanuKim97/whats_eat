package com.example.whats_eat.common

import com.google.android.gms.location.Priority

object Constant {
    const val PLACE_PHOTO_API_URI: String = "https://maps.googleapis.com/maps/api/place/photo"
    const val MAPS_PRIORITY_HIGH: Int = Priority.PRIORITY_HIGH_ACCURACY
    const val MAPS_INTERVAL_MILLIS: Long = 5000L
}