package com.example.data.util

import com.example.database.model.UserCollectionEntity
import com.example.model.collection.Collection

fun entityToModelMapper(
    content: UserCollectionEntity
): Collection = Collection(
    id = content.placeID,
    name = content.placeName,
    latLng = content.placeLatLng,
    imgUrl = content.placeImgUrl
)