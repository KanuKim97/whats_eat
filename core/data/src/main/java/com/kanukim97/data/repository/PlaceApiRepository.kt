package com.kanukim97.data.repository

import com.kanukim97.model.network.detailPlace.DetailedResult
import com.kanukim97.model.network.nearBySearch.NearBySearchResult
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun nearByPlace(latLng: String): Flow<List<NearBySearchResult>>

    fun detailedPlace(placeID: String): Flow<DetailedResult?>
}