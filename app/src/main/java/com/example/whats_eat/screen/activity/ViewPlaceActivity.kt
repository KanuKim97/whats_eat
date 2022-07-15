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
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.commonModel.Results
import com.example.whats_eat.data.model.commonModel.modelContent.photo.Photos
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

    lateinit var paramPlaceID: String
    lateinit var paramPhotos: Array<Photos>

    var mDetailedPlace: PlaceDetail? = null
    var mSelectedPlace: Results? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        mService = RetrofitClient.DetailPlaceApiService

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")
                .child("${auth.currentUser?.uid}")
                .child("Collection")

        /*
         -- Test Code --
        paramPlaceID = intent.getStringExtra("place_id").toString()
        paramPhotos = intent.getSerializableExtra("photos") as Array<Photos>
        */

    }

    override fun onResume() {
        super.onResume()

        controlView()
        //getDetailPlace()

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
        viewPlaceBinding.rating.rating = mSelectedPlace!!.rating.toFloat()

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

    }

    private fun getDetailPlace(
        place_ID: String,
    ) {
        val mDetailedApiResponse = RetrofitRepo.getPlaceDetailSingleton(
            Place_ID = place_ID,
            Api_key = BuildConfig.GOOGLE_API_KEY )

        mDetailedApiResponse.enqueue(object: Callback<PlaceDetail> {
            override fun onResponse(
                call: Call<PlaceDetail>,
                response: Response<PlaceDetail>
            ) {

                if(response.isSuccessful) {

                    mDetailedPlace = response.body()

                    viewPlaceBinding.placeName.text =
                        mDetailedPlace!!.result!!.name
                    viewPlaceBinding.placeAddress.text =
                        mDetailedPlace!!.result!!.formatted_address

                } else {

                    val errorJsonObject: JSONObject
                    val requestErrorBody: ErrorResponse

                    try {

                        errorJsonObject = JSONObject(response.errorBody()!!.string())

                        val responseCode = errorJsonObject.getString("status")
                        val responseMsg = errorJsonObject.getString("error_message")

                        requestErrorBody = ErrorResponse(responseCode, responseMsg)

                        Log.d("error", requestErrorBody.error_message)

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

        val photoRefUrl = StringBuilder(Constant.IPlacePhotoAPIUri)
        photoRefUrl
            .append("?maxwidth=${Constant.photoMaxWidth}")
            .append("&photo_reference=$photoReference")
            .append("&key=${BuildConfig.GOOGLE_API_KEY}")

        return photoRefUrl.toString()
    }

}