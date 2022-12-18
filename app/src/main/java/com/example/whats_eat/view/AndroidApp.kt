package com.example.whats_eat.view

import android.app.Application
import android.content.Context
import com.google.firebase.database.FirebaseDatabase

class AndroidApp: Application() {
    init { instance = this }

    companion object {
        lateinit var instance : AndroidApp

        fun getApplicationContext(): Context = instance.applicationContext
    }
}