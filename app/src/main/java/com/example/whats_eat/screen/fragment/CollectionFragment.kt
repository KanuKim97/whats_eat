package com.example.whats_eat.screen.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.data.collectionController.CollectionAdapter
import com.example.whats_eat.data.model.nearByPlace.PlaceData
import com.example.whats_eat.databinding.FragmentCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class CollectionFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var collectionAdapter: CollectionAdapter

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private lateinit var collectionBinding: FragmentCollectionBinding
    private lateinit var placeArray: ArrayList<PlaceData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
            .child("userInfo")
            .child(auth.currentUser!!.uid)
            .child("Collection")

    }


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        collectionBinding = FragmentCollectionBinding.inflate(layoutInflater)

        recyclerView = collectionBinding.collectionView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        placeArray = arrayListOf()

        collectionAdapter = CollectionAdapter(placeArray)

        return collectionBinding.root

    }

    override fun onResume() {
        super.onResume()
        collectionEventListener()
    }

    private fun collectionEventListener() {

        databaseReference.addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {

                        try{

                            if(snapshot.exists()) {

                                for(placeSnapshot in snapshot.children) {
                                    val place = placeSnapshot.getValue(PlaceData::class.java)
                                    placeArray.add(place!!)
                                }

                                recyclerView.adapter = CollectionAdapter(placeArray)

                            }


                        } catch (e: DatabaseException) { e.printStackTrace() }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            requireContext(),
                            error.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                })

    }

}