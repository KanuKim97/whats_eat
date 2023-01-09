package com.example.whats_eat.view.fragment

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.whats_eat.BuildConfig
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Common
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.remote.model.errorModel.ErrorResponse
import com.example.whats_eat.data.remote.model.nearByPlace.Myplaces
import com.example.whats_eat.data.remote.IGoogleAPIService
import com.example.whats_eat.data.remote.RetrofitClient

import com.example.whats_eat.view.activity.ViewPlaceActivity
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsFragment : Fragment() {
    private var myLatitude: Double = 0.0
    private var myLongitude: Double = 0.0
    private var mapsFragment: SupportMapFragment? = null

    private lateinit var myLatLng: String
    private lateinit var myMap: GoogleMap

    private lateinit var myLastLocation: Location
    private lateinit var locationCallback: LocationCallback
    private lateinit var myFusedLocationClient: FusedLocationProviderClient
    private lateinit var myLocationRequest: LocationRequest
    private lateinit var mGoogleApiService: IGoogleAPIService

    lateinit var currentPlace: Myplaces

    private val mapsCallback =
        OnMapReadyCallback { myGoogleMap ->
            myMap = myGoogleMap

 //           enableMyLocation()

            myMap.isBuildingsEnabled = false
            myMap.uiSettings.isZoomControlsEnabled = true

            myMap.setOnMarkerClickListener{ marker ->
                Common.placeResultData =
                    currentPlace.results!![Integer.parseInt(marker.snippet.toString())]

                startActivity(Intent(requireContext(), ViewPlaceActivity::class.java))

                true
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mGoogleApiService = RetrofitClient.PlaceApiService

        myFusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        myLocationRequest =
            LocationRequest.create().apply {
                this.priority = Priority.PRIORITY_HIGH_ACCURACY
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

        mapsFragment = this.childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapsFragment!!.getMapAsync(mapsCallback)

    }

    override fun onResume() {
        super.onResume()

 //       checkUserPermission()

        locationCallback = object: LocationCallback() {

            override fun onLocationResult(myPoint: LocationResult) {

                myLastLocation = myPoint.locations[myPoint.locations.size-1]

                myLatitude = myLastLocation.latitude
                myLongitude = myLastLocation.longitude
                myLatLng = "$myLatitude,$myLongitude"

                myMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(myLatitude, myLongitude)))
                myMap.animateCamera(CameraUpdateFactory.zoomTo(12f))

//                getNearbyPlace(myLatLng)
            }

        }

/*        myFusedLocationClient.requestLocationUpdates(
            myLocationRequest,
            locationCallback,
            Looper.getMainLooper()
        )*/

    }

    override fun onLowMemory() {
        super.onLowMemory()

        myLocationRequest.priority = Priority.PRIORITY_LOW_POWER
        myLocationRequest.interval = 10000
        myLocationRequest.fastestInterval = 5000

    }

    override fun onStop() {
        super.onStop()
        myMap.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapsFragment?.onDestroy()
    }

/*
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
*/

/*
    private fun getNearbyPlace(
        currentMyLatLng: String
    ) {
        val mNearByApiResponse = RetrofitRepo
            .getNearbySingleton(
                latLng= currentMyLatLng,
                radius= "1000",
                type = "restaurant",
                Api_key = BuildConfig.GOOGLE_API_KEY )

        mNearByApiResponse.enqueue(object: Callback<Myplaces> {

            override fun onResponse(
                call: Call<Myplaces>,
                response: Response<Myplaces>
            ) {

                when(response.code()) {

                    200 -> {
                        currentPlace = response.body()!!

                        println(response.body().toString())

                        for (i in currentPlace.results?.indices!!) {
                            val markerOption = MarkerOptions()

                            val googlePlace = currentPlace.results?.get(i)
                            val placeLat = googlePlace?.geometry!!.location!!.lat
                            val placeLng = googlePlace.geometry!!.location!!.lng
                            val placeName = googlePlace.name
                            val placeLatLng = LatLng(placeLat, placeLng)

                            markerOption.position(placeLatLng)
                            markerOption.title(placeName)

                            markerOption.icon(
                                BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                            )

                            markerOption.snippet(i.toString())
                            myMap.addMarker(markerOption)

                        }

                    }

                    400 -> {

                        val errorJsonObject: JSONObject
                        val requestErrorBody: ErrorResponse

                        try {

                            errorJsonObject = JSONObject(response.errorBody()!!.string())

                            val responseCode = errorJsonObject.getString("status")
                            val responseMsg = errorJsonObject.getString("error_message")

                            requestErrorBody = ErrorResponse(responseCode, responseMsg)

                            Toast.makeText(
                                requireContext(),
                                requestErrorBody.error_message,
                                Toast.LENGTH_SHORT
                            ).show()


                        } catch (e: JSONException) { e.printStackTrace() }

                    }

                }

            }

            override fun onFailure(call: Call<Myplaces>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

        })

    }*/


}