package com.example.whats_eat

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.whats_eat.Common.Common
import com.example.whats_eat.Model.Myplaces
import com.example.whats_eat.remote.IGoogleAPIService

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.*
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import java.util.jar.Manifest
import javax.security.auth.callback.Callback

class Maps : Fragment() {
    private var mMap : GoogleMap? = null

    private var latitude : Double = 0.toDouble()
    private var longitude : Double = 0.toDouble()

    private lateinit var mLastLocation : Location
    private var mMarker : Marker? = null

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest : com.google.android.gms.location.LocationRequest
    lateinit var locationCallback : LocationCallback

    private var mServices : IGoogleAPIService? = null
    internal lateinit var currentPlace : Myplaces


    companion object{
        private const val MY_PERMISSION_CODE : Int = 1000
    }


    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap!!.isMyLocationEnabled = true
        mMap!!.uiSettings.isZoomControlsEnabled=true
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Permission Check
        checkPermission()

        //Request
        buildLocationRequest()

        //callback
        locationCallback = object : LocationCallback(){
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0.locations.get(p0.locations.size-1)

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude

                val latLng = LatLng(latitude, longitude)
                val markerOptions = MarkerOptions()
                        .position(latLng)
                        .title("Your Here")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))

                if(mMarker != null){
                    mMarker!!.remove()
                } else {
                    mMarker = mMap?.addMarker(markerOptions)
                    mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                }

                mMap?.moveCamera(CameraUpdateFactory.zoomBy(12f))
            }
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        startLocationUpdate()

        //Init Service
        nearByPlace(latitude,longitude,"restaurant")
        mServices = Common.googleApiService


        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    //Location Request
    private fun buildLocationRequest() {
        locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 3000
        locationRequest.fastestInterval = 1000
        locationRequest.smallestDisplacement = 10f
    }


    // Permission
    private fun checkPermission() : Boolean {
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED ) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION))
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                                    android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), MY_PERMISSION_CODE)
                else
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    ), MY_PERMISSION_CODE)
                return false
        } else
            return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            MY_PERMISSION_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                        if(checkPermission()) {
                            mMap!!.isMyLocationEnabled = true
                        }
                }
                else{
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    //near by place
    private fun nearByPlace(latitude: Double, longitude: Double ,typePlace: String){
        // Clear all marker on Maps
        mMap?.clear()
        // build URL request based on location

        val url = getUrl(latitude, longitude, typePlace)

        mServices?.getNearbyPlaces(url)
                ?.enqueue(object : retrofit2.Callback<Myplaces>{
                    override fun onResponse(call: Call<Myplaces>, response: Response<Myplaces>) {
                        currentPlace = response.body()!!

                        if(response.isSuccessful){
                            for(element in response.body()!!.results!!){
                                val markerOptions = MarkerOptions()
                                val googlePlace = element
                                val lat = googlePlace.geometry!!.location!!.lat
                                val lng = googlePlace.geometry!!.location!!.lng
                                val placeName = googlePlace.name
                                val latLng = LatLng(lat, lng)

                                markerOptions.position(latLng)
                                markerOptions.title(placeName)

                                if(typePlace == "restaurant"){
                                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_food_bank_24))
                                } else {
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                }

                                markerOptions.snippet(element.toString())
                                mMap!!.addMarker(markerOptions)
                            }

                        }

                    }

                    override fun onFailure(call: Call<Myplaces>, t: Throwable) {
                        Toast.makeText(requireContext(), ""+t.message, Toast.LENGTH_SHORT).show()
                    }

                })
    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyBqA8YJbptuRz5dwzWVMP7kTKEEyg1dYaM")

        Log.d("URL_debug", googlePlaceUrl.toString())
        return googlePlaceUrl.toString()
    }


    @SuppressLint("MissingPermission")
    private fun startLocationUpdate(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun stopLocationUpdate(){
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdate()
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdate()
    }


}