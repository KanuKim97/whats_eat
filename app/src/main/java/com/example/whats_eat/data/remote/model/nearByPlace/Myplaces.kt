package com.example.whats_eat.data.remote.model.nearByPlace

import com.example.whats_eat.data.remote.model.responseModel.Results

data class Myplaces(
    val status: String,
    val results: Array<Results>?
)