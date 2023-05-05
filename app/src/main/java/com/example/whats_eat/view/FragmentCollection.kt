package com.example.whats_eat.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.view.adapter.CollectionAdapter
import com.example.whats_eat.databinding.FragmentCollectionBinding
import com.example.whats_eat.viewModel.CollectionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentCollection: Fragment() {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private var _collectionBinding: FragmentCollectionBinding? = null
    private val collectionBinding get() = _collectionBinding!!
    private val collectionViewModel by viewModels<CollectionViewModel>()

    private val collectionRecyclerView: RecyclerView by lazy { collectionBinding.collectionView }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _collectionBinding = FragmentCollectionBinding.inflate(layoutInflater)
        return collectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCollectionView()
        setCollectionAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _collectionBinding = null
    }

    private fun initCollectionView(): Job = lifecycleScope.launch(mainDispatcher) {
        collectionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun setCollectionAdapter(): Job = lifecycleScope.launch(mainDispatcher) {
        collectionViewModel.collectionFlow.collect {
            Log.d(Constant.LOG_TAG, it.toString())
            collectionRecyclerView.adapter = CollectionAdapter(it)
        }
    }
}