package com.example.whats_eat.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.view.recyclerViewAdapter.CollectionAdapter
import com.example.whats_eat.databinding.FragmentCollectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        return collectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        collectionRecyclerView = collectionBinding.collectionView
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.setHasFixedSize(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _collectionBinding = null
    }

}