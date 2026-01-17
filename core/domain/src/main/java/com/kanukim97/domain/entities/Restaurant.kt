package com.kanukim97.domain.entities

/**
 * ### RestaurantItems
 *
 * This data class used in
 *
 * @param id
 * @param name
 * @param imageUrl
 * @param rating
 * @param reviewsCount
 * @param distance
 */
data class RestaurantItems(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val rating: String,
    val reviewsCount: Int?,
    val distance: Double?
)

/**
 * ### RestaurantInformation
 *
 * This data class used in
 *
 * @param id
 * @param name
 * @param imageUrl
 * @param rating
 * @param reviewsCount
 * @param address
 * @param phoneNumber
 * @param latitude
 * @param longitude
 * @param isOpenNow
 * @param url
 * @param review
 */
data class RestaurantInformation(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val rating: String,
    val reviewsCount: Int?,
    val address: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double,
    val isOpenNow: Boolean?,
    val url: String,
    val review: List<Review> = emptyList()
)

/**
 *
 */
data class RestaurantCollectionItem(
    val id: String,
    val name: String,
    val latLng: String,
    val imageUrl: String = ""
)