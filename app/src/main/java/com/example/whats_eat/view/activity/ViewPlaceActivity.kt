package com.example.whats_eat.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.viewModel.ViewModelFactory

class ViewPlaceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var placeViewModel: DetailPlaceViewModel
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding

    private var placeName: String? = null
    private var placeAddress: String? = null
    private var photoRef: String? = null
    private var rating: String? = null
    private var openTime: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        placeViewModel = ViewModelProvider(this, vmFactory)[DetailPlaceViewModel::class.java]
        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)

        placeName = intent.getStringExtra("placeName")
        placeAddress = intent.getStringExtra("placeAddress")
        photoRef = intent.getStringExtra("photoRef")
        rating = intent.getStringExtra("rating")
        openTime = intent.getStringExtra("openTime")
    }

    override fun onResume() {
        super.onResume()

        controlView(
            placeName.toString(),
            placeAddress.toString(),
            photoRef.toString(),
            rating!!.toFloat(),
            openTime.toBoolean()
        )

        viewPlaceBinding.showMap.setOnClickListener(this)
        viewPlaceBinding.addCollection.setOnClickListener(this)
        viewPlaceBinding.backToMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.backToMap -> {}
            R.id.showMap -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("")))
            R.id.addCollection ->
                placeViewModel.storeCollection(
                    placeName.toString(),
                    placeAddress.toString(),
                    rating!!.toFloat(),
                    photoRef.toString()
                )
        }
    }

    private fun controlView(
        placeName: String,
        placeAddress: String,
        photoRef: String,
        rating: Float,
        openTime: Boolean
    ) {
       if(photoRef.isEmpty()) {
            viewPlaceBinding.placeImg.visibility = View.GONE
        } else {
            Glide.with(this)
                .load(placeViewModel.getPhotoUrl(photoRef))
                .into(viewPlaceBinding.placeImg)
        }

        when {
            openTime -> viewPlaceBinding.openTime.text = "영업 중"
            !openTime -> viewPlaceBinding.openTime.text = "영업 종료"
            null == true -> viewPlaceBinding.openTime.visibility = View.GONE
        }

        viewPlaceBinding.rating.rating = rating
        viewPlaceBinding.placeName.text = placeName
        viewPlaceBinding.placeAddress.text = placeAddress
    }
}