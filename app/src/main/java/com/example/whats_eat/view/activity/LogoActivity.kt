package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.example.whats_eat.view.MainActivity
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.LogoViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

class LogoActivity : AppCompatActivity() {
    private lateinit var logoBinding: ActivityLogoBinding
    private lateinit var logoViewModel: LogoViewModel
    private lateinit var vmFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vmFactory = ViewModelFactory(appRepo = AppRepository())
        logoViewModel = ViewModelProvider(this, vmFactory)[LogoViewModel::class.java]
        logoBinding = ActivityLogoBinding.inflate(layoutInflater)

        setContentView(logoBinding.root)
    }

    override fun onResume() {
        super.onResume()
        logoViewModel.checkUserSession()

        logoViewModel.readFireAuth.observe(this) {
            if (it) { startActivity(Intent(this, MainActivity::class.java)) }
            else { startActivity(Intent(this, LoginActivity::class.java)) }
        }
    }

}