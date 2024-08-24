package com.example.model.domain

data class CollectionModel(
    val id: String,
    val name: String,
    val latLng: String,
    val imgUrl: String = ""
)