package com.example.whats_eat.screen.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Common
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.commonModel.Results
import com.example.whats_eat.data.model.detailPlace.ViewPlaceModel
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.data.model.detailPlace.PlaceDetail
import com.example.whats_eat.data.model.errorModel.ErrorResponse
import com.example.whats_eat.data.remote.RetrofitClient
import com.example.whats_eat.data.remote.RetrofitRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONException
import org.json.JSONObject
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

    private var mSelectedPlace: Results? = null
    private var mDetailedPlace: PlaceDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        mService = RetrofitClient.PlaceApiService
        mSelectedPlace = Common.placeResultData

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
            .child("userInfo")
            .child("${auth.currentUser?.uid}")
            .child("Collection")

    }

    override fun onResume() {
        super.onResume()

        getDetailPlace()
        controlView()

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

                } else { onBackPressed() }

            }

        }

    }

    private fun controlView() {

        if(mSelectedPlace?.photos.isNullOrEmpty()) {
            viewPlaceBinding.placeImg.visibility = View.GONE
        } else {

            Glide
                .with(this)
                .load(getPhotoUrl(mSelectedPlace!!.photos!![0].photo_reference!!))
                .into(viewPlaceBinding.placeImg)

        }

        if(mSelectedPlace!!.opening_hours == null) {
            viewPlaceBinding.openTime.visibility = View.GONE
        } else {
            val openTime: Boolean = mSelectedPlace!!.opening_hours!!.open_now

            if(!openTime){ viewPlaceBinding.openTime.text = "영업 종료" }
            else { viewPlaceBinding.openTime.text = "영업 중" }
        }

        viewPlaceBinding.rating.rating = mSelectedPlace!!.rating.toFloat()

        viewPlaceBinding.placeName.text = mDetailedPlace?.result?.name
        viewPlaceBinding.placeAddress.text = mDetailedPlace?.result?.formatted_address

    }

    private fun getDetailPlace() {
        val mDetailedApiResponse = RetrofitRepo
            .getPlaceDetailSingleton(
                Place_ID = mSelectedPlace?.place_id!!,
                Api_key = BuildConfig.GOOGLE_API_KEY )

        mDetailedApiResponse.enqueue(object: Callback<PlaceDetail> {

            override fun onResponse(
                call: Call<PlaceDetail>,
                response: Response<PlaceDetail>
            ) {

                if(response.isSuccessful) {

                    Log.d("response Code", response.code().toString())
                    Log.d("response Body", response.body()?.status.toString())
                    Log.d("response Msg", response.body()?.result.toString())

                    if(response.body()?.status == "OK") { mDetailedPlace = response.body() }
                    else {

                        Toast.makeText(
                            baseContext,
                            response.body()?.status,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {

                    val errorJsonObject: JSONObject
                    val requestErrorBody: ErrorResponse

                    try {

                        errorJsonObject = JSONObject(response.errorBody()!!.string())

                        val responseCode = errorJsonObject.getString("status")
                        val responseMsg = errorJsonObject.getString("error_message")

                        requestErrorBody = ErrorResponse(responseCode, responseMsg)

                        Toast.makeText(
                            baseContext,
                            requestErrorBody.error_message,
                            Toast.LENGTH_SHORT
                        ).show()

                    } catch (e: JSONException) { e.printStackTrace() }

                }

            }

            override fun onFailure(call: Call<PlaceDetail>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun getPhotoUrl(
        photoReference: String
    ): String {

        return StringBuilder(Constant.IPlacePhotoAPIUri)
            .append("?maxwidth=${Constant.photoMaxWidth}")
            .append("&photo_reference=$photoReference")
            .append("&key=${BuildConfig.GOOGLE_API_KEY}").toString()

    }

}