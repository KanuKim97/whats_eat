package com.kanukim97.data.mapper

import com.kanukim97.database.model.CollectionEntity
import com.kanukim97.model.domain.CollectionModel

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