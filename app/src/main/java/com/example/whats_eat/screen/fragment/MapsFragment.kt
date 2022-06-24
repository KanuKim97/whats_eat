package com.example.whats_eat.screen.fragment

import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.jar.Manifest

class MapsFragment : Fragment() {

    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0

    private lateinit var myMap: GoogleMap

    private lateinit var myLastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var myFusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocationRequest: LocationRequest

    lateinit var myMapService: IGoogleAPIService
    lateinit var currentPlace: Myplaces

    private val mapsCallback =
        OnMapReadyCallback { myGoogleMap ->
            myMap = myGoogleMap

            enableMyLocation()

            myMap.isBuildingsEnabled = false
            myMap.uiSettings.isZoomControlsEnabled = true

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapsFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapsFragment.getMapAsync(mapsCallback)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun enableMyLocation() {

        if(
            ContextCompat
                .checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) { myMap.isMyLocationEnabled = true }
        else { checkUserPermission() }

    }

    private fun checkUserPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        requireActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
            ) {

                ActivityCompat
                    .requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.Location_PERMISSION_CODE
                    )

            } else
                ActivityCompat
                    .requestPermissions(
                        requireActivity(),
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.Location_PERMISSION_CODE
                    )

                return false


        } else
            return true

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode) {

            Constant.Location_PERMISSION_CODE -> {

                if(
                    (grantResults.isEmpty())
                    &&
                    (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ){

                    if(
                        ContextCompat
                            .checkSelfPermission(
                                requireContext(),
                                android.Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                    ) {

                        if(checkUserPermission()) {
                            myMap.isMyLocationEnabled = true
                        }

                    }

                }

            }

        }

    }

    private fun locationCallBack() {

        locationCallback = object: LocationCallback() {

            override fun onLocationResult(myPoint: LocationResult) {

                myLastLocation = myPoint.locations[myPoint.locations.size-1]

                myLatitude = myLastLocation.latitude
                myLongitude = myLastLocation.longitude

                myMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(myLatitude, myLongitude)))
                myMap.animateCamera(CameraUpdateFactory.zoomTo(12f))



            }

        }

    }

}