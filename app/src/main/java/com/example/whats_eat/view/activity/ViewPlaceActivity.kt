package com.example.whats_eat.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.RequestManager
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.viewModel.activity.DetailPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ViewPlaceActivity() : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding
    @Inject lateinit var glideModule: RequestManager

    private val placeViewModel: DetailPlaceViewModel by viewModels()
    private val placeName: String? by lazy { getPlaceName() }
    private val placeAddress: String? by lazy { getPlaceAddress() }
    private val placePhotoRef: String? by lazy { getPlacePhotoRef() }
    private val placeOpenTime: String? by lazy { getOpenTime() }
    private val placeRatingNumber: String? by lazy { getRatingNumber() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)

        viewPlaceBinding.showMap.setOnClickListener(this)
        viewPlaceBinding.addCollection.setOnClickListener(this)
        viewPlaceBinding.backToMap.setOnClickListener(this)

        setContentView(viewPlaceBinding.root)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.backToMap -> {}
            R.id.showMap -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("")))
        }
    }

    private fun displayContent() {
        viewPlaceBinding.placeName.text = placeName
        viewPlaceBinding.placeAddress.text = placeAddress
        viewPlaceBinding.rating.rating = placeRatingNumber?.toFloat()!!

        when(placeOpenTime.toBoolean()) {
            true -> viewPlaceBinding.openTime.text = "영업 중"
            false -> viewPlaceBinding.openTime.text = "영업 종료"
        }

        if (placePhotoRef.isNullOrEmpty()) {
            viewPlaceBinding.placeImg.visibility = View.GONE
        } else {
            glideModule.load(placePhotoRef).into(viewPlaceBinding.placeImg)
        }
    }

    private fun getPlaceName(): String? = intent.getStringExtra("PlaceName")
    private fun getPlaceAddress(): String? = intent.getStringExtra("PlaceAddress")
    private fun getRatingNumber(): String? = intent.getStringExtra("RatingNumber")
    private fun getPlacePhotoRef(): String? = intent.getStringExtra("PlacePhotoRef")
    private fun getOpenTime(): String? = intent.getStringExtra("OpenTime")
}