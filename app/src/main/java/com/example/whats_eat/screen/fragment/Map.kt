package com.example.whats_eat.screen.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Common
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.screen.activity.ViewPlace
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
class Map {
    private var mMap: GoogleMap? = null

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    lateinit var mServices: IGoogleAPIService
    internal lateinit var currentPlace: Myplaces

    private val mapsCallback = OnMapReadyCallback { gMaps ->
        mMap = gMaps

        enabledMyLocation()

        mMap!!.isBuildingsEnabled = false
        mMap!!.uiSettings.isZoomControlsEnabled = true

        mMap!!.setOnMarkerClickListener { placeMarker->
            Common.currentPlace =
                currentPlace.results?.get(
                    Integer.parseInt(placeMarker.snippet.toString())
                )

            startActivity(Intent(requireContext(), ViewPlace::class.java))
            true
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient =
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

        val mapsFragment =
            childFragmentManager.findFragmentById(R.id.map)
                    as SupportMapFragment

        mapsFragment.getMapAsync(mapsCallback)

    }

    override fun onResume() {
        super.onResume()

        checkLocationPermission()
        buildLocationRequest()
        locationCallback()

        fusedLocationProviderClient
            .requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

    }

    private fun checkLocationPermission(): Boolean {
        if(ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION )
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ), Constant.Location_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ), Constant.Location_PERMISSION_CODE)
            return false
        }
        else
            return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){

            Constant.Location_PERMISSION_CODE -> {
                if(grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION )
                        == PackageManager.PERMISSION_GRANTED)
                        if(checkLocationPermission()) {
                            mMap!!.isMyLocationEnabled = true
                        }
                }
                else {
                    Toast.makeText(requireContext(), "Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun buildLocationRequest(){
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 1000
            smallestDisplacement = 10f
        }
    }

    private fun locationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                mLastLocation = p0.locations[p0.locations.size-1]

                latitude = mLastLocation.latitude
                longitude = mLastLocation.longitude
                val latLng = LatLng(latitude, longitude)

                mMap?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap?.animateCamera(CameraUpdateFactory.zoomTo(12f))

                mServices = Common.googleApiService

                nearByPlace()

            }
        }
    }

    private fun enabledMyLocation() {

        if(ContextCompat
                .checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
        ) { mMap!!.isMyLocationEnabled = true }
        else { checkLocationPermission() }

    }

    */
/*
    private fun nearByPlace() {

    }
    *//*



    private fun nearByPlace() {
        mMap?.clear()

        val url = getUrl(latitude, longitude)

        Log.d("Url", url)
        Log.d("Key", R.string.API_KEYS.toString())

        mServices.getNearbyPlaces(url).enqueue(object : Callback<Myplaces> {
            override fun onResponse(call: Call<Myplaces>, response: Response<Myplaces>) {

                currentPlace = response.body()!!

                if (response.isSuccessful) {
                    for (i in response.body()!!.results!!.indices) {
                        val markerOptions = MarkerOptions()

                        val googlePlace = response.body()!!.results?.get(i)
                        val lat = googlePlace?.geometry!!.location!!.lat
                        val lng = googlePlace.geometry!!.location!!.lng
                        val placeName = googlePlace.name
                        val latLng = LatLng(lat, lng)

                        markerOptions.position(latLng)
                        markerOptions.title(placeName)

                        if ((R.string.TypePlace).equals("restaurant")) {
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

    private fun getUrl(latitude: Double, longitude: Double): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=1000")
        googlePlaceUrl.append("&type=${R.string.TypePlace}")
        googlePlaceUrl.append("&key=${R.string.API_KEYS}")

        Log.d("Url",googlePlaceUrl.toString())

        return googlePlaceUrl.toString()
    }


}*/
