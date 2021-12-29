package com.example.whats_eat

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.whats_eat.common.Common
import com.example.whats_eat.common.Constant
import com.example.whats_eat.model.*
import com.example.whats_eat.dataModel.ViewPlaceModel
import com.example.whats_eat.remote.IGoogleAPIService

import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlace : AppCompatActivity() {
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding
    private lateinit var mService : IGoogleAPIService
    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth : FirebaseAuth

    var mPlace : PlaceDetail? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        mService = Common.googleApiService

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        databaseReference = database.reference.child("userInfo")
                .child("${auth.currentUser?.uid}")
                .child("Collection")


        if(Common.currentPlace!!.photos != null && Common.currentPlace!!.photos!!.isNotEmpty()) {
            Glide.with(this)
                .load(getPhotoPlace(Common.currentPlace!!.photos!![0].photo_reference!!, 1000))
                .into(viewPlaceBinding.placeImg)
        }

        if(Common.currentPlace!!.rating != null) {
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
                .enqueue(object : Callback<PlaceDetail> {
                    override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {
                        mPlace = response.body()

                        viewPlaceBinding.placeName.text = mPlace!!.result!!.name
                        viewPlaceBinding.placeAddress.text = mPlace!!.result!!.formatted_address
                    }

                    override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                        Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                    }
                })

        viewPlaceBinding.showMap.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(mPlace!!.result!!.url)))
        }

        viewPlaceBinding.addCollection.setOnClickListener {

            val restaurantName : String = mPlace!!.result!!.name.toString()
            val restaurantAddress : String = mPlace!!.result!!.formatted_address.toString()
            val restaurantRating : Float = Common.currentPlace!!.rating.toFloat()
            val restaurantPhotos : String

            if(Common.currentPlace!!.photos == null) {
                restaurantPhotos = null.toString()

                val placeDetailArray = ViewPlaceModel(
                        restaurantName,
                        restaurantAddress,
                        restaurantRating,
                        restaurantPhotos)

                databaseReference.push().setValue(placeDetailArray)

            } else {

                restaurantPhotos = getPhotoPlace(Common.currentPlace!!.photos!![0].photo_reference!!, 1000)

                val placeDetailArray = ViewPlaceModel(
                        restaurantName,
                        restaurantAddress,
                        restaurantRating,
                        restaurantPhotos)

                databaseReference.push().setValue(placeDetailArray)
            }

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