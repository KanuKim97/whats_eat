package com.example.whats_eat.data.remote.model.nearByPlace

import com.example.whats_eat.data.remote.model.response.Results

data class Myplaces(
    val status: String,
    val results: ArrayList<Results>
)