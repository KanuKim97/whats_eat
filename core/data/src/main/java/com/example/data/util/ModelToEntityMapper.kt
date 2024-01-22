package com.example.data.util

import com.example.database.model.CollectionEntity
import com.example.model.collection.Collection

fun modelToEntityMapper(
    content: Collection
): CollectionEntity = CollectionEntity(
    placeID = content.id,
    placeName = content.name,
    placeLatLng = content.latLng,
    placeImgUrl = content.imgUrl
)