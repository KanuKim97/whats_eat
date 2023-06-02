package com.example.data.repository

import com.example.domain.model.placeItem.response.Results
import com.example.domain.repository.PlaceApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PlaceApiRepositoryImpl @Inject constructor(
    private val placeApiRepositoryBaseImpl: PlaceApiRepositoryBaseImpl
): PlaceApiRepository {
    override fun getMainBannerItems(latLng: String): Flow<ArrayList<Results>> = flow {
        placeApiRepositoryBaseImpl.nearByPlace(latLng).filter { items ->
            items.removeAll(
                items.filter { item ->
                    item.rating == 0.0 ||
                            item.rating == null ||
                            item.place_id == null ||
                            item.photos?.get(0)?.photo_reference == null
                }.toSet()
            )
        }
    }

    override fun getSubBannerItems(latLng: String): Flow<ArrayList<Results>>  = flow {
        placeApiRepositoryBaseImpl.nearByPlace(latLng).filter { items ->
            items.removeAll(
                items.filter { item ->
                    item.rating == 0.0 ||
                            item.rating == null ||
                            item.photos?.get(0)?.photo_reference == null
                }.toSet()
            )
        }
    }
}