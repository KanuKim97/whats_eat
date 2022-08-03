package com.example.whats_eat.data.model.responseModel

import com.example.whats_eat.data.model.responseModel.modelContent.loaction.Geometry
import com.example.whats_eat.data.model.responseModel.modelContent.period.OpeningHours
import com.example.whats_eat.data.model.responseModel.modelContent.photo.Photos

class Results {
    var name : String? = null
    var geometry : Geometry? = null

    var photos : Array<Photos>? = null
    var id : String? = null
    var place_id : String? = null
    var rating : Double = 0.0
    var opening_hours : OpeningHours? = null

    var url : String? = null
    var formatted_address : String? = null
}