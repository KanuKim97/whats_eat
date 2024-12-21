package com.kanukim97.domain.model

data class CollectionDomainModel(
    val id: String,
    val name: String,
    val latLng: String,
    val imgUrl: String = ""
)
