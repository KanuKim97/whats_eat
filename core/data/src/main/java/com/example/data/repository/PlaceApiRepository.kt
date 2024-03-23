package com.example.data.repository

import com.example.model.details.DetailedResult
import com.example.model.nearBySearch.NearBySearchResult
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun nearByPlace(latLng: String): Flow<List<NearBySearchResult>>

    fun detailedPlace(placeID: String): Flow<DetailedResult?>
}