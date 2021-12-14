package com.example.whats_eat

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission


import com.example.whats_eat.Common.Common
import com.example.whats_eat.Common.Constant
import com.example.whats_eat.Model.Myplaces
import com.example.whats_eat.remote.IGoogleAPIService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.StringBuilder

class Maps : Fragment() {
    private var mMap: GoogleMap? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: com.google.android.gms.location.LocationRequest
    private lateinit var locationCallback: LocationCallback
    lateinit var mServices: IGoogleAPIService

    internal lateinit var currentPlace: Myplaces


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap

        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.isMyLocationEnabled = true

        mMap!!.setOnMarkerClickListener { marker ->
            Common.currentPlace = currentPlace.results?.get(Integer.parseInt(marker.snippet))
            startActivity(Intent(requireContext(), viewPlace::class.java))
            true
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(callback)

    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        checkPermission()
        buildLocationRequest()
        locationCallback()

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        fusedLocationProviderClient
            .requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
        )

        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    private fun locationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0.locations.get(p0.locations.size - 1)

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude, longitude)

                mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap?.moveCamera(CameraUpdateFactory.zoomTo(12f))

                mServices = Common.googleApiService

                nearByPlace("restaurant")
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 10f
    }


    private fun nearByPlace(typePlace: String) {
        mMap?.clear()

        val url = getUrl(latitude, longitude, typePlace)

        mServices.getNearbyPlaces(url).enqueue(object : Callback<Myplaces> {
            override fun onResponse(call: Call<Myplaces>, response: Response<Myplaces>) {

                currentPlace = response.body()!!

                if (response.isSuccessful) {
                    for (i in 0 until response.body()!!.results!!.size) {
                        val markerOptions = MarkerOptions()

                        val googlePlace = response.body()!!.results?.get(i)
                        val lat = googlePlace?.geometry!!.location!!.lat
                        val lng = googlePlace.geometry!!.location!!.lng
                        val placeName = googlePlace.name
                        val latLng = LatLng(lat, lng)

                        markerOptions.position(latLng)
                        markerOptions.title(placeName)

                        if (typePlace == "restaurant") {
                            markerOptions.icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_BLUE
                                )
                            )
                        } else {
                            markerOptions.icon(
                                BitmapDescriptorFactory.defaultMarker(
                                    BitmapDescriptorFactory.HUE_GREEN
                                )
                            )
                        }

                        markerOptions.snippet(i.toString())
                        mMap?.addMarker(markerOptions)
                    }
                }
            }

            override fun onFailure(call: Call<Myplaces>, t: Throwable) {
                Toast.makeText(requireContext(), "$t Error plz try again", Toast.LENGTH_SHORT)
                    .show()
                onDestroy()
            }
        })

    }


    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=${Constant.API_KEYS}")

        return googlePlaceUrl.toString()
    }


    //TODO : Permission Code
    private fun checkPermission(): Boolean {
        if (checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat
                    .shouldShowRequestPermissionRationale(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
            ) {

                ActivityCompat
                    .requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.Location_PERMISSION_CODE )

            } else {

                ActivityCompat
                    .requestPermissions(
                        requireActivity(),
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constant.Location_PERMISSION_CODE )

            }
            return false
        } else {
            return true
        }
    }


}