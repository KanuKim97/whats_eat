package com.example.whats_eat.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.data.remote.model.detailPlace.PlaceDetail
import com.example.whats_eat.viewModel.ViewModelFactory

class ViewPlaceActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var placeViewModel: DetailPlaceViewModel
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        placeViewModel = ViewModelProvider(this, vmFactory)[DetailPlaceViewModel::class.java]
        viewPlaceBinding = ActivityViewPlaceBinding.inflate(layoutInflater)
        setContentView(viewPlaceBinding.root)
    }

    override fun onResume() {
        super.onResume()

        viewPlaceBinding.showMap.setOnClickListener(this)
        viewPlaceBinding.addCollection.setOnClickListener(this)
        viewPlaceBinding.backToMap.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.backToMap -> {}
            R.id.showMap -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("")))
            R.id.addCollection -> {
                val placeName: String = viewPlaceBinding.placeName.text.toString()
                val placeAddress: String = viewPlaceBinding.placeAddress.text.toString()
                val placeRating: Float = 4.9F //TODO: RateData
                val placePhoto: String = ""
                placeViewModel.storeCollection(placeName, placeAddress, placeRating, placePhoto)
            }
        }
    }

    private fun controlView(mPlaceDetailData: PlaceDetail?) {
       /* if(mSelectedPlace?.photos.isNullOrEmpty()) {
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
        viewPlaceBinding.placeName.text = mPlaceDetailData?.result?.name
        viewPlaceBinding.placeAddress.text = mPlaceDetailData?.result?.formatted_address*/
    }
}