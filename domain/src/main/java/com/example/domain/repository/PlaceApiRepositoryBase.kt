package com.example.domain.repository

import com.example.domain.model.placeItem.response.Results
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepositoryBase {
    fun nearByPlace(latLng: String): Flow<ArrayList<Results>>

    fun detailedPlace(place_Id: String): Flow<Results?>
}