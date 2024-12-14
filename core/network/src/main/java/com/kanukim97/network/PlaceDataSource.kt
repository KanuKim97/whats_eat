package com.example.network

import com.example.model.network.detailPlace.DetailedPlace
import com.example.model.network.nearBySearch.NearBySearch

interface PlaceDataSource {
    suspend fun getDetail(placeID: String): DetailedPlace

    suspend fun getNearBySearch(latLng: String): NearBySearch
}