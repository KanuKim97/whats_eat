package com.example.whats_eat.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.databinding.FragmentMapsBinding
import com.example.whats_eat.viewModel.fragment.MapsViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MapsFragment: Fragment() {
    private var _mapsFragmentBinding: FragmentMapsBinding?= null
    private val mapsFragmentBinding get() = _mapsFragmentBinding!!
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mapsFragmentBinding = FragmentMapsBinding.inflate(inflater, container, false)


        return mapsFragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mapsFragmentBinding = null
    }
}