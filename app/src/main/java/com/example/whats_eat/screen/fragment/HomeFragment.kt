package com.example.whats_eat.screen.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.whats_eat.data.model.nearByPlace.PlaceData
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        databaseReference = database.reference
            .child("userInfo")
            .child(auth.currentUser!!.uid)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        return homeBinding.root
    }

    override fun onResume() {
        super.onResume()
        loadHomeInfo()
    }

    private fun loadHomeInfo() {

        databaseReference.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                try {

                    if(snapshot.exists()) {

                        homeBinding.UserTxt.text =
                            snapshot.child("userName").value.toString()

                       for(placeSnapshot in snapshot.child("Collection").children) {

                           val placeValue = placeSnapshot.getValue(PlaceData::class.java)
                           Log.d("PlaceData", "$placeValue")

                           homeBinding.placeName1.text = placeValue?.placeName
                           homeBinding.placeName2.text = placeValue?.placeName
                           homeBinding.placeName3.text = placeValue?.placeName
                           homeBinding.placeName4.text = placeValue?.placeName
                       }

                    }

                } catch (e:DatabaseException) { e.printStackTrace() }

            /*
                if(snapshot.exists()) {

                    homeBinding.UserTxt.text =
                        snapshot.child("userName").value.toString()

                } else {

                    Toast.makeText(
                        requireContext(),
                        "collection is not exist",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            */

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }

}