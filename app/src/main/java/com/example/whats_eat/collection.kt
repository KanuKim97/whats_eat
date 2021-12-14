package com.example.whats_eat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.Collection_Controller.collectionAdapter
import com.example.whats_eat.DataModel.placeData
import com.example.whats_eat.databinding.FragmentCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class collection : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var placeArray: ArrayList<placeData>
    private lateinit var collectionAdapter: collectionAdapter

    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    private lateinit var collectionBinding: FragmentCollectionBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        collectionBinding = FragmentCollectionBinding.inflate(layoutInflater)

        recyclerView = collectionBinding.collectionView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        placeArray = arrayListOf<placeData>()

        collectionAdapter = collectionAdapter(placeArray)

        collectionEventListener()

        return collectionBinding.root
    }

    private fun collectionEventListener() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
                .child("userInfo")
                .child(auth.currentUser!!.uid)
                .child("Collection")

        databaseReference.addValueEventListener(object : ValueEventListener{

                    override fun onDataChange(snapshot: DataSnapshot) {

                        if(snapshot.exists()) {

                            for(placeSnapshot in snapshot.children) {

                                val place = placeSnapshot.getValue(placeData::class.java)
                                Log.d("placeString", place.toString())
                                placeArray.add(place!!)
                            }

                            recyclerView.adapter = collectionAdapter(placeArray)

                        }
                    }

                    override fun onCancelled(error: DatabaseError) { }
                })

    }

}