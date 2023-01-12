package com.example.whats_eat.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.fragment.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HomeFragment : Fragment() {
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        homeViewModel = ViewModelProvider(this, vmFactory)[HomeViewModel::class.java]
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
        homeViewModel.loadHomeInformation()
        homeViewModel.userData.observe(this) { homeBinding.UserTxt.text = it }
    }

}