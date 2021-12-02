package com.example.whats_eat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.whats_eat.Common.Common
import com.example.whats_eat.Common.Constant
import com.example.whats_eat.Model.*
import com.example.whats_eat.remote.IGoogleAPIService

import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

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
            Log.d("rating", Common.currentPlace!!.rating.toString())
            viewPlaceBinding.rating.rating = Common.currentPlace!!.rating.toFloat()
        } else {
            viewPlaceBinding.rating.visibility = View.GONE
        }

        if(Common.currentPlace!!.opening_hours != null) {
            val openTime : Boolean = Common.currentPlace!!.opening_hours!!.open_now

            if(!openTime){
                viewPlaceBinding.openTime.text = "영업 종료"
            } else {
                viewPlaceBinding.openTime.text = "영업 중"
            }

        } else {
            viewPlaceBinding.openTime.visibility = View.GONE
        }

        mService.getDetailPlace(getPlaceDetailUrl(Common.currentPlace!!.place_id))
                .enqueue(object : Callback<placeDetail> {
                    override fun onResponse(call: Call<placeDetail>, response: Response<placeDetail>) {
                        mPlace = response.body()

                        viewPlaceBinding.placeName.text = mPlace!!.result!!.name
                        viewPlaceBinding.placeAddress.text = mPlace!!.result!!.formatted_address
                    }

                    override fun onFailure(call: Call<placeDetail>, t: Throwable) {
                        Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })

        viewPlaceBinding.showMap.setOnClickListener {
        // need to write it    
        // startActivity(Intent(this, Maps::class.java))
        }
    }

    private fun getPlaceDetailUrl(placeId: String?): String {
        val placeDetailUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
        placeDetailUrl.append("?place_id=$placeId")
        placeDetailUrl.append("&key=${Constant.API_KEYS}")

        return placeDetailUrl.toString()
    }

    private fun getPhotoPlace(photoReference: String, maxWidth: Int): String {

        val placePhotoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        placePhotoUrl.append("?maxwidth=$maxWidth")
        placePhotoUrl.append("&photo_reference=$photoReference")
        placePhotoUrl.append("&key=${Constant.API_KEYS}")

        return placePhotoUrl.toString()

    }
}