package com.example.whats_eat.view.fragment

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.data.di.coroutineDispatcher.MainDispatcher
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.example.whats_eat.viewModel.fragment.HomeViewModel
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment() {
    @Inject @MainDispatcher lateinit var mainDispatcher: CoroutineDispatcher
    @Inject @IoDispatcher lateinit var ioDispatcher: CoroutineDispatcher
    @Inject lateinit var locationRequest: CurrentLocationRequest

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private val myLocationManger: LocationManager by lazy { setLocationManager() }
    private val myFusedLocationClient: FusedLocationProviderClient by lazy { setFusedLocationClient() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!checkGPSON()) {
            Toast.makeText(
                requireContext(),
                getString(R.string.ActiveGPS),
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            getCurrentLocation()
        }

        homeViewModel.nearByPlace.observe(viewLifecycleOwner) {
            Log.d(Constant.LOG_TAG, "${it[0].name}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    private fun checkGPSON(): Boolean =
        myLocationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun setLocationManager(): LocationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun setFusedLocationClient(): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(requireContext())

    private fun getCurrentLocation(): Job = lifecycleScope.launch(ioDispatcher) {
        try {
            myFusedLocationClient.getCurrentLocation(locationRequest, object: CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }).addOnCompleteListener { location ->
                geoCoderService(location.result.latitude, location.result.longitude)
                searchNearByRestaurant(location.result.latitude, location.result.longitude)
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    private fun geoCoderService(
        lat: Double,
        lng: Double
    ): Job = lifecycleScope.launch(mainDispatcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val subLocalList: List<Address>? = withContext(ioDispatcher) {
                Geocoder(requireContext(), Locale.KOREA).getFromLocation(lat, lng, 5)
            }

            for (element in 0..subLocalList!!.lastIndex) {
                if (subLocalList[element].subLocality != null) {
                    homeBinding.locationTxt.text = subLocalList[element].subLocality
                    break
                }
            }
        }
    }

    private fun searchNearByRestaurant(lat: Double, lng: Double) {
        val latLng: String = latLngStringBuilder(lat, lng)
        homeViewModel.searchNearByPlace(latLng)
    }

    private fun latLngStringBuilder(lat: Double, lng: Double): String =
        StringBuilder("$lat, $lng").toString()

}