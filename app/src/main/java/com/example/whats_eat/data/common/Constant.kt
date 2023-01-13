package com.example.whats_eat.data.common

import com.google.android.gms.location.Priority

object Constant {
    const val Location_PERMISSION_CODE: Int = 1
    const val photoMaxWidth: Int = 1000
    const val Location_Radius: String = "1000"
    const val Location_Type: String = "restaurant"
    const val IPlaceAPIUri: String = "https://maps.googleapis.com/maps/api/place/"
    const val IPlacePhotoAPIUri: String = "https://maps.googleapis.com/maps/api/place/photo"
    const val mapPriority_High: Int = Priority.PRIORITY_HIGH_ACCURACY
    const val mapUpdateDistanceMeters: Float = 1000f
    const val mapIntervalMills: Long = 5000
}