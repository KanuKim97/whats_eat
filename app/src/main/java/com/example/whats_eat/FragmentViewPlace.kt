package com.example.whats_eat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whats_eat.common.Common
import com.example.whats_eat.common.Constant
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.databinding.FragmentViewPlaceBinding
import com.example.whats_eat.model.PlaceDetail
import com.example.whats_eat.remote.IGoogleAPIService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.StringBuilder

class FragmentViewPlace : Fragment() {
    private lateinit var viewPlaceBinding : FragmentViewPlaceBinding
    private lateinit var mService : IGoogleAPIService
    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var auth : FirebaseAuth

    var mPlace : PlaceDetail? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewPlaceBinding = FragmentViewPlaceBinding.inflate(layoutInflater)
        mService = Common.googleApiService

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")
            .child("${auth.currentUser?.uid}")
            .child("collection")



        return viewPlaceBinding.root
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