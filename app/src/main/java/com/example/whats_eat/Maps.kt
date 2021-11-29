package com.example.whats_eat

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
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
import retrofit2.Callback
import java.lang.StringBuilder

class Maps : Fragment(R.layout.fragment_maps) {
    private var mMap : GoogleMap? = null

    private var latitude : Double = 0.toDouble()
    private var longitude : Double = 0.toDouble()

    private lateinit var mLastLocation : Location
    private var mMarker : Marker? = null

    lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    lateinit var locationRequest : com.google.android.gms.location.LocationRequest
    lateinit var locationCallback : LocationCallback

    lateinit var mServices : IGoogleAPIService
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

                mMap?.moveCamera(CameraUpdateFactory.zoomBy(15f))
                mServices = Common.googleApiService
                //Init Service
                nearByPlace("restaurant")

            }
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        startLocationUpdate()



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
    private fun nearByPlace(typePlace: String){
        // Clear all marker on Maps
        mMap?.clear()

        val url = getUrl(latitude, longitude, typePlace)

        mServices?.getNearbyPlaces(url)?.enqueue(object : Callback<Myplaces>{
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
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        } else {
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        }

                        markerOptions.snippet(i.toString())

                        mMap?.addMarker(markerOptions)
                    }
                }
            }

            override fun onFailure(call: Call<Myplaces>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000")
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