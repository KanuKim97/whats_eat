package com.example.whats_eat.view.fragment

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.databinding.FragmentMapsBinding
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
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment: Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {
    private lateinit var gMapView: MapView
    @Inject lateinit var currentLocation: CurrentLocationRequest
    private val placeMarkerOptions: MarkerOptions by lazy { setMarkerOption() }
    private val myLocationManager: LocationManager by lazy { setLocationManager() }
    private val myFusedLocationClient: FusedLocationProviderClient by lazy { setFusedLocationClient() }

    private var _mapsFragmentBinding: FragmentMapsBinding? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestLocationPermission()
        gMapView = mapsFragmentBinding.gMap
        gMapView.onCreate(savedInstanceState)

        if (checkGPSOn()) {
            gMapView.getMapAsync(this)
            searchNearByPlace()
            getCurrentLocation()
        } else {
            Toast.makeText(requireContext(), "GPS 기능이 꺼져있습니다.", Toast.LENGTH_SHORT).show()
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).run { startActivity(this) }
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        updateGMapsUI(gMap)
        getDeviceLocation(gMap)
        markPlaceOnGMaps(gMap)
    }

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

    private fun setFusedLocationClient(): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(requireContext())

    private fun setLocationManager(): LocationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun setMarkerOption(): MarkerOptions =
        MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))

    private fun searchNearByPlace(): Job = mapsViewModel.searchNearByPlace()

    private fun getDeviceLocation(gMap: GoogleMap): Job = CoroutineScope(Dispatchers.Main).launch {
        try {
            val locationResult = withContext(Dispatchers.IO) { myFusedLocationClient.lastLocation }
            locationResult.addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    gMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(it.result.latitude, it.result.longitude), 15f
                        )
                    )
                } else { it.exception?.printStackTrace() }
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }


    private fun getCurrentLocation(): Job = CoroutineScope(Dispatchers.IO).launch {
        try {
            myFusedLocationClient.getCurrentLocation(currentLocation, object: CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }).addOnSuccessListener { location ->
                Log.d(Constant.TAG, "${location?.latitude}")
                Log.d(Constant.TAG, "${location?.longitude}")
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

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

    private fun markPlaceOnGMaps(gMap: GoogleMap) =
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

    private fun checkGPSOn() = myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

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