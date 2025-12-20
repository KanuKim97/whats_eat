package com.kanukim97.network

import com.kanukim97.model.network.detailPlace.DetailedPlace
import com.kanukim97.model.network.nearBySearch.NearBySearch

interface PlaceDataSource {
    suspend fun getDetail(placeID: String): DetailedPlace

    suspend fun getNearBySearch(latLng: String): NearBySearch
}