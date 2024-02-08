package com.example.model.network

data class Review(
    val author_name: String,
    val author_url: String,
    val language: String,
    val original_language: String,
    val profile_photo_url: String,
    val rating: Int,
    val relative_time_description: String,
    val text: String,
    val time: Int,
    val translated: Boolean
)