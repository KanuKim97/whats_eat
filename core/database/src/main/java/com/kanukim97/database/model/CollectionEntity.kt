package com.kanukim97.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Collection_Entity")
data class CollectionEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("placeName") val name: String,
    @ColumnInfo("placeImgUrl") val imageUrl: String,
    @ColumnInfo("placeLatLng") val latLng: String
)