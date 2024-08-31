package com.example.data

import com.example.model.domain.CollectionModel
import com.example.model.network.CurrentOpeningHours
import com.example.model.network.Geometry
import com.example.model.network.OpeningHours
import com.example.model.network.detailPlace.DetailedResult

internal object DataLayerDummyData {
    val DUMMY_COLLECTION_DATA = listOf(
        CollectionModel(
            id =  "1",
            name = "default",
            latLng = "default",
            imgUrl = "default"
        ),
        CollectionModel(
            id =  "2",
            name = "default",
            latLng = "default",
            imgUrl = "default"
        ),
        CollectionModel(
            id =  "3",
            name = "default",
            latLng = "default",
            imgUrl = "default"
        )
    )

    val DUMMY_DETAIL_PLACE_DATA = DetailedResult(
        addressComponents = emptyList(),
        adrAddress = "xx시 xx구",
        businessStatus = "영업 중",
        currentOpeningHours = CurrentOpeningHours(
            openNow = true,
            periods = emptyList(),
            weekdayText = emptyList()
        ),
        delivery = true,
        dineIn = true,
        formattedAddress = "xx시 xx구 xx동",
        formattedPhoneNumber = "000-0000-0000",
        geometry = Geometry(),
        icon = "",
        iconBackgroundColor = "",
        iconMaskBaseUri = "",
        internationalPhoneNumber = "",
        name = "",
        openingHours = OpeningHours(),
    )

    const val DUMMY_LAT_LNG = "37.594321,127.0329403"
    const val DUMMY_PLACE_ID = "ChIJN1t_tDeuEmsRUsoyG83frY4"
}