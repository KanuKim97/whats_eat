package com.kanukim97.data.mapper

import com.kanukim97.data.BuildConfig
import com.kanukim97.data.model.DetailPlaceResult
import com.kanukim97.data.model.PlaceCollection
import com.kanukim97.database.model.CollectionEntity
import com.kanukim97.remote.response.detailPlace.DetailedResultResponse

fun entityToModelMapper(
    entity: CollectionEntity
): PlaceCollection = PlaceCollection(
    id = entity.placeID,
    name = entity.placeName,
    latLng = entity.placeLatLng,
    imageUrl = entity.placeImgUrl
)

fun modelToEntityMapper(
    model: PlaceCollection
): CollectionEntity = CollectionEntity(
    placeID = model.id,
    placeName = model.name,
    placeLatLng = model.latLng,
    placeImgUrl = model.imageUrl
)

fun DetailedResultResponse?.toDataModel(): DetailPlaceResult? {
    if (this == null) return null

    return DetailPlaceResult(
        id = this.placeId ?: "",
        name = this.name ?: "",
        imageUrls = with(this.photos) {
            if (this == null) return@with emptyList()

            this.map { photo -> photo.getFullPhotoReference(BuildConfig.API_KEY) }
        },
        rating = this.rating,
        address = this.formattedAddress ?: "",
        phoneNumber = this.formattedPhoneNumber ?: "",
        latitude = this.geometry?.location?.lat ?: 0.0,
        longitude = this.geometry?.location?.lng ?: 0.0,
        isOpened = this.currentOpeningHours?.openNow ?: false
    )
}