package com.example.whats_eat.view.fragment

import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.databinding.FragmentMapsBinding
import com.example.whats_eat.viewModel.fragment.MapsViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class MapsFragment: Fragment(), OnMapReadyCallback {
    private lateinit var gMapView: MapView
    private lateinit var myFusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocationRequest: LocationRequest

    private var lastKnownLocation: Location? = null

    // Fragment ViewBinding
    private var _mapsFragmentBinding: FragmentMapsBinding? = null
    private val mapsFragmentBinding get() = _mapsFragmentBinding!!
    
    // MapsFragment ViewModel - ktx
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mapsFragmentBinding = FragmentMapsBinding.inflate(inflater, container, false)

        /* Create MapView */
        gMapView = mapsFragmentBinding.gMap
        gMapView.onCreate(savedInstanceState)
        gMapView.getMapAsync(this)

        return mapsFragmentBinding.root
    }

    override fun onMapReady(gMap: GoogleMap) {
        val point = LatLng(37.514655, 126.979974)
        gMap.addMarker(MarkerOptions().position(point).title("현위치"))
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15f))
    }

    /* MapView LifeCycle */
    override fun onStart() {
        super.onStart()
        gMapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        gMapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        gMapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        gMapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        gMapView.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _mapsFragmentBinding = null
    }

    /* getDeviceLocation display on Google Maps */
    private fun getDeviceLocation(gMap: GoogleMap) {
        try {
            if (checkLocationPermission()) {
                val locationResult = myFusedLocationClient.lastLocation

                locationResult.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result

                        if (lastKnownLocation != null) {
                            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), 15f))
                        }
                    } else {
                        task.exception?.printStackTrace()
                    }
                }
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }


    /* Google Maps UI Setting */
    private fun updateGMapsUI(gMap: GoogleMap) {
        try {
            if(checkLocationPermission()) {
                gMap.isBuildingsEnabled = false
                gMap.isMyLocationEnabled = true
                gMap.uiSettings.isMyLocationButtonEnabled = true
                gMap.uiSettings.isZoomControlsEnabled = true
            } else {
                gMap.isMyLocationEnabled = false
                gMap.uiSettings.isMyLocationButtonEnabled = false
                gMap.uiSettings.isZoomControlsEnabled = false
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    /* check Location Permission */
    private fun checkLocationPermission() = EasyPermissions.hasPermissions(
        requireContext(),
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
}