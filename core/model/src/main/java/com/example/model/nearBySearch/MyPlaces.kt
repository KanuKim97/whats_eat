package com.example.model.nearBySearch

import com.example.model.response.Results

data class MyPlaces(
    val status: String,
    val results: ArrayList<Results>
)