package com.example.whats_eat.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.view.recyclerViewAdapter.CollectionAdapter
import com.example.whats_eat.data.remote.model.nearByPlace.PlaceData
import com.example.whats_eat.databinding.FragmentCollectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList

class CollectionFragment: Fragment() {
    private lateinit var collectionRecyclerView: RecyclerView
    private lateinit var collectionAdapter: CollectionAdapter

    private var _collectionBinding: FragmentCollectionBinding? = null
    private val collectionBinding get() = _collectionBinding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _collectionBinding = FragmentCollectionBinding.inflate(layoutInflater)
        collectionRecyclerView = collectionBinding.collectionView
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.setHasFixedSize(true)

        return collectionBinding.root
    }

}