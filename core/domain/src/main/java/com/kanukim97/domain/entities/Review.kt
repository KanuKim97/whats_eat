package com.kanukim97.domain.entities

data class Review(
    val authorName: String,
    val profilePhotoUrl: String?,
    val rating: Int?,
    val content: String?
)