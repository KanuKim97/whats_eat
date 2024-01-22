package com.example.data.util

import com.example.database.model.CollectionEntity
import com.example.model.collection.Collection

fun entityToModelMapper(
    content: CollectionEntity
): Collection = Collection(
    id = content.placeID,
    name = content.placeName,
    latLng = content.placeLatLng,
    imgUrl = content.placeImgUrl
)