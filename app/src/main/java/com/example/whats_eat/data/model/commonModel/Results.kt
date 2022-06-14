package com.example.whats_eat.data.model.commonModel

import com.example.whats_eat.data.model.commonModel.modelContent.loaction.Geometry
import com.example.whats_eat.data.model.commonModel.modelContent.period.OpeningHours
import com.example.whats_eat.data.model.commonModel.modelContent.photo.Photos
import com.example.whats_eat.data.model.commonModel.modelContent.review.Review

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
    var reviews : Array<Review>? = null

}