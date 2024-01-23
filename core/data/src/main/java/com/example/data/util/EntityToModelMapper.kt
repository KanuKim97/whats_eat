package com.example.data.util

import com.example.database.model.CollectionEntity
import com.example.model.feature.CollectionModel

fun entityToModelMapper(
    content: CollectionEntity
): CollectionModel = CollectionModel(
    id = content.placeID,
    name = content.placeName,
    latLng = content.placeLatLng,
    imgUrl = content.placeImgUrl
)