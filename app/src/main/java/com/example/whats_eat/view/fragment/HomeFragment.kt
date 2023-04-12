package com.example.whats_eat.view.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.IoDispatcher
import com.example.whats_eat.databinding.FragmentHomeBinding
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
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment(), EasyPermissions.PermissionCallbacks {
    @Inject @IoDispatcher lateinit var ioDispatcher: CoroutineDispatcher
    @Inject lateinit var locationRequest: CurrentLocationRequest
    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!

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
        alertGPSOnOff()
        checkLocationPermission()
        getCurrentLocation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    private fun alertGPSOnOff() {
        if (!checkGPSON()) {
            Toast.makeText(requireContext(), "GPS를 활성화 해주세요", Toast.LENGTH_SHORT).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
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
                Log.d(Constant.LOG_TAG, "${location.result.latitude}")
                Log.d(Constant.LOG_TAG, "${location.result.longitude}")
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    private fun checkLocationPermission(): Boolean =
        EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission(): Unit = EasyPermissions.requestPermissions(
        requireActivity(),
        "이 애플리케이션은 위치정보 사용 허가가 필요합니다.",
        Constant.LOCATION_PERMISSION_CODE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {  }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
            requestLocationPermission()
        }
    }


}