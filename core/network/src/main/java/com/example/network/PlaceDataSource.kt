package com.example.network

import com.example.model.details.DetailedPlace
import com.example.model.nearBySearch.NearBySearch

interface PlaceDataSource {
    suspend fun getDetail(placeID: String): DetailedPlace

    suspend fun getNearBySearch(latLng: String): NearBySearch
}