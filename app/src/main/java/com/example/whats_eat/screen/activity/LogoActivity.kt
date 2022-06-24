package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.whats_eat.screen.MainActivity
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.google.firebase.auth.FirebaseAuth

class LogoActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var logoBinding: ActivityLogoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logoBinding = ActivityLogoBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        setContentView(logoBinding.root)
    }

    override fun onResume() {
        super.onResume()

        val user = auth.currentUser

        Handler(
            Looper.getMainLooper()
        ).postDelayed({

            if(user != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }, 5000)

    }

}