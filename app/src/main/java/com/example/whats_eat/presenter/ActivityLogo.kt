package com.example.whats_eat.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import android.Manifest
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.example.whats_eat.viewModel.LogoViewModel
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class ActivityLogo : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    private val logoBinding by lazy { ActivityLogoBinding.inflate(layoutInflater) }
    private val logoViewModel: LogoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUI()
        setContentView(logoBinding.root)
    }

    private fun updateUI(): Unit =
        logoViewModel.readFireAuth.observe(this) { isSessionAlive ->
            if (hasLocationPermission()) {
                if(isSessionAlive) {
                    startActivity(Intent(this, ActivityMain::class.java))
                    finish()
                } else {
                    startActivity(Intent(this, ActivityLogIn::class.java))
                    finish()
                }
            } else {
                requestLocationPermission()
            }
        }

    private fun hasLocationPermission(): Boolean =
        EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)

    private fun requestLocationPermission(): Unit = EasyPermissions.requestPermissions(
        this,
        getString(R.string.Location_Permission),
        Constant.LOCATION_PERMISSION_CODE,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) = updateUI()

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionDenied(this, perms.first())) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermission()
        }
    }

}