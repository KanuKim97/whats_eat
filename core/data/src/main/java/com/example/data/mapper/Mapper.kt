package com.example.data.mapper

import com.example.database.model.CollectionEntity
import com.example.model.collection.CollectionModel

fun entityToModelMapper(
    entity: CollectionEntity
): CollectionModel = CollectionModel(
    id = entity.placeID,
    name = entity.placeName,
    latLng = entity.placeLatLng,
    imgUrl = entity.placeImgUrl
)

fun modelToEntityMapper(
    model: CollectionModel
): CollectionEntity = CollectionEntity(
    placeID = model.id,
    placeName = model.name,
    placeLatLng = model.latLng,
    placeImgUrl = model.imgUrl
)