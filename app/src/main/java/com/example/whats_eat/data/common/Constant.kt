package com.example.whats_eat.data.common

import com.google.android.gms.location.Priority

object Constant {
    const val LOCATION_PERMISSION_CODE: Int = 1
    const val PHOTO_MAX_WIDTH: Int = 1000
    const val LOCATION_RADIUS: String = "1000"
    const val LOCATION_TYPE: String = "restaurant"
    const val PLACE_API_URI: String = "https://maps.googleapis.com/maps/api/place/"
    const val PLACE_PHOTO_API_URI: String = "https://maps.googleapis.com/maps/api/place/photo"
    const val DEFAULT_LOCATION: String = "37.514655, 126.97974"
    const val MAPS_PRIORITY_HIGH: Int = Priority.PRIORITY_HIGH_ACCURACY
    const val MAPS_UPDATE_DISTANCE_METERS: Float = 1000f
    const val MAPS_INTERVAL_MILLIS: Long = 5000L
    const val DELAY_TIME_MILLIS: Long = 1000L

    const val LOG_TAG: String = "로그"
}