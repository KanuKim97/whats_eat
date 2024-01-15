package com.example.data.repository

import com.example.model.response.Results
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun nearByPlace(latLng: String): Flow<ArrayList<Results>>

    fun detailedPlace(placeID: String): Flow<Results?>
}