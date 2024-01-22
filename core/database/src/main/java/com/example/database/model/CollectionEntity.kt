package com.example.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Collection_Entity")
data class CollectionEntity(
    @PrimaryKey val placeID: String,
    @ColumnInfo("placeName") val placeName: String,
    @ColumnInfo("placeImgUrl") val placeImgUrl: String,
    @ColumnInfo("placeLatLng") val placeLatLng: String
)