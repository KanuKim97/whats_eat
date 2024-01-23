package com.example.model.nearBySearch

import com.example.model.response.Results

data class NearBySearch(
    val html_attributions: List<Any>,
    val results: ArrayList<Results>,
    val status: String
)