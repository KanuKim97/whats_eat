package com.example.data.util

import com.example.database.model.UserCollectionEntity
import com.example.model.collection.Collection

fun modelToEntityMapper(
    content: Collection
): UserCollectionEntity = UserCollectionEntity(
    placeID = content.id,
    placeName = content.name,
    placeLatLng = content.latLng,
    placeImgUrl = content.imgUrl
)