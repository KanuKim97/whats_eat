package com.example.whats_eat.data.common

import com.google.android.gms.location.Priority

/* Constant Value */
object Constant {
    const val LOCATION_PERMISSION_CODE: Int = 1

    const val LOCATION_PRIORITY: Int = Priority.PRIORITY_HIGH_ACCURACY
    const val DURATION_MILLIS: Long = 5000L

    const val PHOTO_MAX_WIDTH: Int = 1000

    const val LOCATION_RADIUS: String = "1000"
    const val LOCATION_TYPE: String = "restaurant"
    const val DEFAULT_LAT_LNG: String = "37.5665, 126.9780"

    const val PLACE_API_URL: String = "https://maps.googleapis.com/maps/api/place/"
    const val PLACE_PHOTO_API_URL: String = "https://maps.googleapis.com/maps/api/place/photo"

    const val TAG: String = "로그"
}