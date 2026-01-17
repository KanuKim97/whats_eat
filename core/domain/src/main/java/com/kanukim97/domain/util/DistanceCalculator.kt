package com.kanukim97.domain.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


private const val EARTH_RADIUS_IN_KM = 6371.0

/**
 * ### calculateDistance
 *
 * This object class calculates the distance between two points on the Earth's surface with using the Haversine formula.
 *
 * @param startLat The latitude of the starting point in degrees.
 * @param startLng The longitude of the starting point in degrees.
 * @param endLat The latitude of the ending point in degrees.
 * @param endLng The longitude of the ending point in degrees.
 *
 * @return The distance between the two points in kilometers.
 */
fun calculateDistance(
    startLat: Double,
    startLng: Double,
    endLat: Double,
    endLng: Double
): Double {
    val dLat = Math.toRadians(endLat - startLat)
    val dLng = Math.toRadians(endLng - startLng)

    val a = sin(dLat / 2).pow(2.0) +
            cos(Math.toRadians(startLat)) * cos(Math.toRadians(endLat)) *
            sin(dLng / 2).pow(2.0)

    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return EARTH_RADIUS_IN_KM * c
}
