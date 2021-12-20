package com.example.whats_eat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whats_eat.databinding.ActivityViewPlaceBinding
import com.example.whats_eat.model.PlaceDetail
import com.example.whats_eat.remote.IGoogleAPIService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FragmentViewPlace : Fragment() {
    private lateinit var viewPlaceBinding : ActivityViewPlaceBinding
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
    ): View? {

        return inflater.inflate(R.layout.fragment_view_place, container, false)
    }

}