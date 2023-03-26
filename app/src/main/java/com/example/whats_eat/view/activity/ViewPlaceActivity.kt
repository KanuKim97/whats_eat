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
}