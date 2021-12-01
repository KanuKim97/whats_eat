package com.example.whats_eat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.whats_eat.Common.Common
import com.example.whats_eat.Model.*
import com.example.whats_eat.remote.IGoogleAPIService

import com.example.whats_eat.databinding.ActivityViewPlaceBinding

class viewPlace : AppCompatActivity() {
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding

    private lateinit var mService : IGoogleAPIService
    var mPlace : placeDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        mService = Common.googleApiService

        viewPlaceBinding.placeName.text=""
        viewPlaceBinding.placeAddress.text=""
        viewPlaceBinding.openTime.text=""

        if(Common.currentPlace!!.photos != null && Common.currentPlace!!.photos!!.isNotEmpty()) {
            Glide.with(this)
                .load(getPhotoPlace(Common.currentPlace!!.photos!![0].photo_reference!!, 1000))
                .into(viewPlaceBinding.placeImg)
        }

        if(Common.currentPlace!!.rating != null) {
            viewPlaceBinding.rating.numStars = Common.currentPlace!!.rating.toInt()
        } else {
            viewPlaceBinding.rating.visibility = View.GONE
        }

        if(Common.currentPlace!!.rating != null) {
            viewPlaceBinding.rating.numStars = Common.currentPlace!!.rating.toInt()
        } else {
            viewPlaceBinding.rating.visibility = View.GONE
        }

        viewPlaceBinding.showMap.setOnClickListener {

        }
    }

    private fun getPhotoPlace(photoReference: String, i: Int): String {

    }
}