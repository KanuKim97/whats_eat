package com.example.domain.repository

import com.example.domain.model.placeItem.response.Results
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun getMainBannerItems(latLng: String): Flow<ArrayList<Results>>

    fun getSubBannerItems(latLng: String): Flow<ArrayList<Results>>
}