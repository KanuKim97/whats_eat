package com.example.data.placeModel.nearByPlace

import com.example.data.placeModel.response.Results

data class MyPlaces(
    val status: String,
    val results: ArrayList<Results>
)