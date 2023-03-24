package com.example.whats_eat.view.fragment

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.databinding.FragmentMapsBinding
import com.example.whats_eat.view.activity.ViewPlaceActivity
import com.example.whats_eat.viewModel.fragment.MapsViewModel
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.CancellationToken
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment: Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks,
    GoogleMap.OnMarkerClickListener {
    private lateinit var gMapView: MapView
    private lateinit var myFusedLocationClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null

    private var _mapsFragmentBinding: FragmentMapsBinding? = null
    private val mapsFragmentBinding get() = _mapsFragmentBinding!!

    private val mapsViewModel: MapsViewModel by viewModels()
    private val placeMarkerOptions: MarkerOptions by lazy { setMarkerOption() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        searchNearByPlace()
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

        requestLocationPermission()

        return mapsFragmentBinding.root
    }

    override fun onMapReady(gMap: GoogleMap) {
        updateGMapsUI(gMap)
        getDeviceLocation(gMap)
        markPlaceOnGMaps(gMap)

        gMap.setOnMarkerClickListener(this)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _mapsFragmentBinding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        gMapView.onDestroy()
    }

    private fun searchNearByPlace() = mapsViewModel.searchNearByPlace()

    /* Get Location and display on Google Maps */
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

    private fun getCurrentLocation() {
        try {
            TODO("Not Implicated")
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    /* Google Maps Marker */
    private fun setMarkerOption(): MarkerOptions =
        MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

    private fun markPlaceOnGMaps(gMap: GoogleMap) {
        mapsViewModel.nearByResponse.observe(viewLifecycleOwner) {
            for (i in it?.results?.indices!!) {
                val placeResultLat: Double = it.results[i].geometry.location.lat
                val placeResultLng: Double = it.results[i].geometry.location.lng
                val placeLatLng = LatLng(placeResultLat, placeResultLng)
                val placeName: String = it.results[i].name

                placeMarkerOptions.position(placeLatLng)
                placeMarkerOptions.title(placeName)
                placeMarkerOptions.snippet(i.toString())
                gMap.addMarker(placeMarkerOptions)
            }
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Intent(requireContext(), ViewPlaceActivity::class.java)
            .apply {
                putExtra("PlaceName", "")
                putExtra("PlaceAddress", "")
                putExtra("RatingNumber", "")
                putExtra("PlacePhotoRef", "")
                putExtra("OpenTime", "")
            }.run { startActivity(this) }

        return true
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
        Constant.LOCATION_PERMISSION_CODE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {  }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else { requestLocationPermission() }
    }

}