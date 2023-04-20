package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import android.Manifest
import com.example.whats_eat.R
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.example.whats_eat.viewModel.LogoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class LogoActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private val logoBinding by lazy { ActivityLogoBinding.inflate(layoutInflater) }
    private val logoViewModel: LogoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUI()
        setContentView(logoBinding.root)
    }

    private fun updateUI(): Unit = logoViewModel.readFireAuth.observe(this) {
        lifecycleScope.launch(mainDispatcher) {
            if (hasLocationPermission()) {
                if(it) {
                    delay(Constant.DELAY_TIME_MILLIS)
                    startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                    finish()
                } else {
                    delay(Constant.DELAY_TIME_MILLIS)
                    startActivity(Intent(this@LogoActivity, LoginActivity::class.java))
                    finish()
                }
            } else {
                requestLocationPermission()
            }
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        updateUI()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionDenied(this, perms.first())) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermission()
        }
    }


}