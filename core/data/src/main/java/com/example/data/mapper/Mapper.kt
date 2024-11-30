package com.example.data.mapper

import com.example.data.model.CollectionDataModel
import com.example.database.model.CollectionEntity

fun entityToDataModelMapper(
    entity: CollectionEntity
): CollectionDataModel = CollectionDataModel(
    id = entity.placeID,
    name = entity.placeName,
    latLng = entity.placeLatLng,
    imgUrl = entity.placeImgUrl
)

fun dataModelToEntityMapper(
    model: CollectionDataModel
): CollectionEntity = CollectionEntity(
    placeID = model.id,
    placeName = model.name,
    placeLatLng = model.latLng,
    placeImgUrl = model.imgUrl
)