package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.example.whats_eat.viewModel.activity.LogoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogoActivity : AppCompatActivity() {
    private lateinit var logoBinding: ActivityLogoBinding
    private val logoViewModel: LogoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logoBinding = ActivityLogoBinding.inflate(layoutInflater)

        logoViewModel.checkUserSession()
        logoViewModel.readFireAuth.observe(this) {
            if(it) { startActivity(Intent(this, MainActivity::class.java)) }
            else { startActivity(Intent(this, LoginActivity::class.java)) }
        }

        setContentView(logoBinding.root)
    }
}