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
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.FragmentMapsBinding
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.fragment.MapsViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import pub.devrel.easypermissions.EasyPermissions

class MapsFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private lateinit var googleMap: GoogleMap
    private lateinit var mylocation: Location
    private lateinit var myLocationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var vmFactory: ViewModelFactory
    private lateinit var mapsViewModel: MapsViewModel

    private var mapFragmentBinding: FragmentMapsBinding?= null
    private val mapsBinding
        get() = mapFragmentBinding!!
    private val googleMapsCallback = OnMapReadyCallback { myGoogleMap ->
        googleMap = myGoogleMap
        updateMapsUI()
        getFuseLastLocation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        mapsViewModel = ViewModelProvider(this, vmFactory)[MapsViewModel::class.java]
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        myLocationRequest = LocationRequest.Builder(Constant.mapIntervalMills)
            .apply {
                this.setPriority(Constant.mapPriority_High)
                this.setMinUpdateDistanceMeters(Constant.mapUpdateDistanceMeters)
            }.build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mapFragmentBinding = FragmentMapsBinding.inflate(inflater, container, false)

        val mapsFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapsFragment.getMapAsync(googleMapsCallback)

        return mapsBinding.root
    }

    override fun onResume() {
        super.onResume()
        requestLocationPermission()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapFragmentBinding = null
    }

    // --- Google Maps Function ---
    @SuppressLint("MissingPermission")
    private fun updateMapsUI() {
        try {
            if(checkLocationPermission()) {
                googleMap.isBuildingsEnabled = false
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
                googleMap.uiSettings.isZoomControlsEnabled = true
            } else {
                googleMap.isMyLocationEnabled = false
                googleMap.uiSettings.isMyLocationButtonEnabled = false
                googleMap.uiSettings.isZoomControlsEnabled = false
                requestLocationPermission()
            }
        } catch (e: SecurityException) { Log.e("Exception: %s", e.message, e) }
    }

    @SuppressLint("MissingPermission")
    private fun getFuseLastLocation() {
        try {
            if(checkLocationPermission()) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        mylocation = task.result
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            LatLng(mylocation.latitude, mylocation.longitude), 15f)
                        )
                    } else {
                        Log.d("Location", "Current Location is null")
                        Log.e("Location", "Exception: %s", task.exception)
                    }
                }
            }
        } catch (e: SecurityException) { Log.e("Exception: %s", e.message, e) }
    }

    // --- Check Location Permission ---
    private fun checkLocationPermission() = EasyPermissions.hasPermissions(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun requestLocationPermission() = EasyPermissions.requestPermissions(
            this,
            "This Application needs Location Permission",
            Constant.Location_PERMISSION_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    @SuppressLint("MissingPermission")
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {  }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Log.d("Permission Denied", "Location Permission Denied")
        } else { requestLocationPermission() }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
}