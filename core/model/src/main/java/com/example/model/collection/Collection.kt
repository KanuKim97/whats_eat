package com.example.model.collection

data class Collection(
    val id: String,
    val name: String,
    val latLng: String,
    val imgUrl: String = ""
)
