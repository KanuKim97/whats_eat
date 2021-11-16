package com.example.whats_eat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth

class logo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        Handler(Looper.getMainLooper()).postDelayed({
            if(user != null){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, logIn::class.java))
                finish()
            }
        }, 5000)
    }
}