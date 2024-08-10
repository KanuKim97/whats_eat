package com.example.data.repository

import com.example.model.network.detailPlace.DetailedResult
import com.example.model.network.nearBySearch.NearBySearchResult
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun nearByPlace(latLng: String): Flow<List<NearBySearchResult>>

    fun detailedPlace(placeID: String): Flow<DetailedResult?>
}