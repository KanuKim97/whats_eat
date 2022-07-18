package com.example.whats_eat.data.model.commonModel.modelContent

import com.example.whats_eat.data.model.commonModel.modelContent.loaction.Geometry
import com.example.whats_eat.data.model.commonModel.modelContent.period.OpeningHours
import com.example.whats_eat.data.model.commonModel.modelContent.photo.Photos
import com.example.whats_eat.data.model.commonModel.modelContent.review.Review
import java.io.Serializable

data class ResultsData (
    var name: String,
    var geometry: Geometry,

    var Id: String,
    var Place_Id: String,
    var rating: Double,

    var openingHours: OpeningHours,
    var url: String,
    var Place_Address: String,

    var placePhotos: Array<Photos>,
    var Reviews: Array<Review>
    ): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResultsData

        if (!placePhotos.contentEquals(other.placePhotos))
            return false

        if (!Reviews.contentEquals(other.Reviews))
            return false

        return true
    }

    override fun hashCode(): Int {
        var result = placePhotos.contentHashCode()
        result = 31 * result + Reviews.contentHashCode()

        return result
    }
}