package com.example.whats_eat.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.example.whats_eat.viewModel.fragment.HomeViewModel
import com.google.firebase.database.*


class HomeFragment: Fragment() {
    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)


        return homeBinding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

}