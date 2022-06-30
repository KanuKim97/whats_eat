package com.example.whats_eat.screen.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whats_eat.R
import com.example.whats_eat.data.model.commonModel.Results
import com.example.whats_eat.data.model.detailPlace.ViewPlaceModel
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.data.model.detailPlace.PlaceDetail
import com.example.whats_eat.data.remote.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class ViewPlaceActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding
    private lateinit var mService : IGoogleAPIService

    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth : FirebaseAuth

    var mDetailedPlace: PlaceDetail? = null
    var mSelectedPlace: Results? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        mService = RetrofitClient.gMapsApiService

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")
                .child("${auth.currentUser?.uid}")
                .child("Collection")

    }

    override fun onResume() {
        super.onResume()

        viewControl()
        serviceCall()

        viewPlaceBinding.showMap.setOnClickListener(this)
        viewPlaceBinding.addCollection.setOnClickListener(this)
        viewPlaceBinding.backToMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id) {
            R.id.backToMap ->
                onBackPressed()

            R.id.showMap ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(mDetailedPlace!!.result!!.url)
                    )
                )

            R.id.addCollection -> {
                val restaurantName: String = mDetailedPlace?.result?.name ?: "null"
                val restaurantAddress: String = mDetailedPlace?.result?.formatted_address ?: "null"
                val restaurantRating: Float = mSelectedPlace!!.rating.toFloat()
                val restaurantPhotos: String

                if(mSelectedPlace?.photos == null) {
                    restaurantPhotos = null.toString()

                    val placeDetailArray = ViewPlaceModel(
                        restaurantName,
                        restaurantAddress,
                        restaurantRating,
                        restaurantPhotos)

                    databaseReference
                        .push()
                        .setValue(placeDetailArray)

                } else {

                    restaurantPhotos =
                        getPhotoPlace(
                            mSelectedPlace?.photos!![0].photo_reference!!,
                            viewPlaceBinding.placeImg.width
                        )

                    val placeDetailArray =
                        ViewPlaceModel(
                            restaurantName,
                            restaurantAddress,
                            restaurantRating,
                            restaurantPhotos
                    )

                    databaseReference
                        .push()
                        .setValue(placeDetailArray)

                    onBackPressed()
                }

            }

        }

    }

    private fun viewControl() {

        if(
            mSelectedPlace!!.photos != null
            &&
            mSelectedPlace!!.photos!!.isNotEmpty()
        ) {

            Glide
                .with(this)
                .load(
                    getPhotoPlace(
                        mSelectedPlace!!.photos!![0].photo_reference!!,
                        maxWidth = 1000
                    )
                )
                .into(viewPlaceBinding.placeImg)

        } else { viewPlaceBinding.placeImg.visibility = View.GONE }

        viewPlaceBinding.rating.rating = mSelectedPlace!!.rating.toFloat()

        if(mSelectedPlace!!.opening_hours != null) {

            val openTime: Boolean = mSelectedPlace!!.opening_hours!!.open_now

            if(!openTime){ viewPlaceBinding.openTime.text = "영업 종료" }
            else { viewPlaceBinding.openTime.text = "영업 중" }

        } else { viewPlaceBinding.openTime.visibility = View.GONE }

    }

    private fun serviceCall(){

        mService.getDetailPlace(getPlaceDetailUrl(mSelectedPlace!!.place_id))
            .enqueue(object : Callback<PlaceDetail> {
                override fun onResponse(call: Call<PlaceDetail>, response: Response<PlaceDetail>) {

                    if(response.isSuccessful){

                        mDetailedPlace = response.body()

                        viewPlaceBinding.placeName.text = mDetailedPlace!!.result!!.name
                        viewPlaceBinding.placeAddress.text = mDetailedPlace!!.result!!.formatted_address

                    } else {

                        Toast.makeText(
                            applicationContext,
                            "response loaded Failed",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }

                override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {

                    Toast.makeText(
                        applicationContext,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })

    }

    private fun getPlaceDetailUrl(placeId: String?): String {

        val placeDetailUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/details/json")
        placeDetailUrl.append("?place_id=$placeId")
        placeDetailUrl.append("&key=${R.string.API_KEYS}")

        return placeDetailUrl.toString()
    }

    private fun getPhotoPlace(photoReference: String, maxWidth: Int): String {

        val placePhotoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        placePhotoUrl.append("?maxwidth=$maxWidth")
        placePhotoUrl.append("&photo_reference=$photoReference")
        placePhotoUrl.append("&key=${R.string.API_KEYS}")

        return placePhotoUrl.toString()
    }
}