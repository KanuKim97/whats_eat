package com.example.whats_eat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.whats_eat.databinding.FragmentDetailPlaceBinding
import com.example.whats_eat.viewModel.DetailPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentDetailPlace : Fragment() {
    private var _detailBinding: FragmentDetailPlaceBinding? = null
    private val detailBinding get() = _detailBinding!!
    private val detailPlaceViewModel: DetailPlaceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _detailBinding = FragmentDetailPlaceBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}