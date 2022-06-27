package com.example.whats_eat.screen.fragment

import android.content.pm.PackageManager
import android.location.Location
import com.google.android.gms.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.RetrofitRepo
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment() {

    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0

    private lateinit var myMap: GoogleMap

    private lateinit var myLastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var myFusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocationRequest: LocationRequest

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

        myLocationRequest =
            LocationRequest.create().apply {
                this.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                this.interval = 5000
                this.fastestInterval = 1000
                this.smallestDisplacement = 10f
            }

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

        checkUserPermission()
        locationCallBack()

        myFusedLocationClient.requestLocationUpdates(
            myLocationRequest,
            locationCallback,
            Looper.getMainLooper()
        )


    }

    override fun onLowMemory() {
        super.onLowMemory()

        myLocationRequest.priority = LocationRequest.PRIORITY_LOW_POWER
        myLocationRequest.interval = 10000
        myLocationRequest.fastestInterval = 5000

    }

    override fun onDestroy() {
        super.onDestroy()
        myMap.clear()
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

    private fun getNearbyPlace() {
        val mNearByApiResponse =
            RetrofitRepo.getNearbySingleton(
                LatLng(myLatitude, myLongitude).toString(),
                radius = "1000",
                R.string.TypePlace.toString(),
                R.string.API_KEYS.toString()
            )

        mNearByApiResponse.enqueue(object: Callback<Myplaces>{

            override fun onResponse(
                call: Call<Myplaces>,
                response: Response<Myplaces>
            ) {

                if(response.isSuccessful) {

                    currentPlace = response.body()!!
                    Log.d("Success", "response is successful")

                } else { Log.d("Error", "response is failed") }

            }

            override fun onFailure(call: Call<Myplaces>, t: Throwable) {
                Log.d("Error", "${t.message}")
                Toast.makeText(
                    requireContext(),
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })

    }


}