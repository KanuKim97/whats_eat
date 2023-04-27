package com.example.whats_eat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.view.adapter.CollectionAdapter
import com.example.whats_eat.databinding.FragmentCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCollection: Fragment() {
    private var _collectionBinding: FragmentCollectionBinding? = null
    private val collectionBinding get() = _collectionBinding!!

    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private val collectionRecyclerView: RecyclerView by lazy { collectionBinding.collectionView }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _collectionBinding = FragmentCollectionBinding.inflate(layoutInflater)
        return collectionBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _collectionBinding = null
    }

    private fun initCollectionView(): Job = lifecycleScope.launch(mainDispatcher) {
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.setHasFixedSize(true)
    }

}