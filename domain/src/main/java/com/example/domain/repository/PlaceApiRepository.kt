package com.example.domain.repository

import com.example.domain.model.PlaceItem
import kotlinx.coroutines.flow.Flow

interface PlaceApiRepository {
    fun getMainBannerItems(latLng: String): Flow<ArrayList<PlaceItem?>>
    fun getSubBannerItems(latLng: String): Flow<ArrayList<PlaceItem?>>
}