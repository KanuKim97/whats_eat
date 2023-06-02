package com.example.data.placeModel.nearByPlace

import com.example.domain.model.placeItem.response.Results

data class MyPlaces(
    val status: String,
    val results: ArrayList<Results>
)