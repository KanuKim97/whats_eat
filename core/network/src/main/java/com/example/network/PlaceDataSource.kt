package com.example.network

import com.example.model.details.DetailedPlace
import com.example.model.nearBySearch.MyPlaces

interface PlaceDataSource {
    suspend fun getDetail(placeID: String): DetailedPlace

    suspend fun getNearBySearch(latLng: String): MyPlaces
}