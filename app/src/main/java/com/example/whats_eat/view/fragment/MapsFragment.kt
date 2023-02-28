package com.example.whats_eat.view.fragment

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
class MapsFragment: Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {
    private lateinit var gMapView: MapView
    private var _mapsFragmentBinding: FragmentMapsBinding? = null
    private val mapsFragmentBinding get() = _mapsFragmentBinding!!
    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _mapsFragmentBinding = FragmentMapsBinding.inflate(inflater, container, false)

        setMapViewVisibility()
        requestLocationPermission()
        
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
                requestLocationPermission()
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    /* check Location Permission */
    private fun checkLocationPermission() = EasyPermissions.hasPermissions(
        requireContext(),
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private fun requestLocationPermission() = EasyPermissions.requestPermissions(
        this,
        "이 애플리케이션은 위치정보 사용 허가가 필요합니다.",
        Constant.Location_PERMISSION_CODE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Toast.makeText(
            requireContext(),
            "권한이 허가되었습니다!",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else { requestLocationPermission() }
    }

    /* According to Location Permission Application can display MapView or Not */
    private fun setMapViewVisibility() {
        if(checkLocationPermission()) {
            mapsFragmentBinding.gMap.visibility = View.VISIBLE
        } else {
            mapsFragmentBinding.gMap.visibility = View.GONE
        }
    }
}