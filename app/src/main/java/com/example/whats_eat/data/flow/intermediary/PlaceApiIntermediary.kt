package com.example.whats_eat.data.flow.intermediary

import com.example.whats_eat.data.flow.producer.PlaceApiProducer
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class PlaceApiIntermediary @Inject constructor(
    private val placeApiProducer: PlaceApiProducer
) {
    fun getMainBannerItem(latLng: String) = placeApiProducer.nearByPlace(latLng).filter { items ->
        items.removeAll(
            items.filter { item ->
                item.rating == 0.0 ||
                        item.rating == null ||
                        item.place_id == null ||
                        item.photos?.get(0)?.photo_reference == null
            }.toSet()
        )
    }

    fun getSubBannerItem(latLng: String) = placeApiProducer.nearByPlace(latLng).filter { items ->
        items.removeAll(
            items.filter { item ->
                item.rating == 0.0 ||
                        item.rating == null ||
                        item.photos?.get(0)?.photo_reference == null
            }.toSet()
        )
    }

}