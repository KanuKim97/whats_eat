package com.example.whats_eat.data.common

import com.google.android.gms.location.Priority

object Constant {
    const val LOCATION_PERMISSION_CODE: Int = 1
    const val PLACE_API_URI: String = "https://maps.googleapis.com/maps/api/place/"
    const val PLACE_PHOTO_API_URI: String = "https://maps.googleapis.com/maps/api/place/photo"
    const val MAPS_PRIORITY_HIGH: Int = Priority.PRIORITY_HIGH_ACCURACY
    const val MAPS_INTERVAL_MILLIS: Long = 5000L
    const val LOG_TAG: String = "로그"
}