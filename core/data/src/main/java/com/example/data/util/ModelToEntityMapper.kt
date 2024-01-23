package com.example.data.util

import com.example.database.model.CollectionEntity
import com.example.model.feature.CollectionModel

fun modelToEntityMapper(
    content: CollectionModel
): CollectionEntity = CollectionEntity(
    placeID = content.id,
    placeName = content.name,
    placeLatLng = content.latLng,
    placeImgUrl = content.imgUrl
)