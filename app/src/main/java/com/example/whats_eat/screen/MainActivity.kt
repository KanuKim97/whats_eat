package com.example.whats_eat.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.whats_eat.R
import com.example.whats_eat.screen.activity.LoginActivity
import com.example.whats_eat.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        val navigationView = mainActivityBinding.navigationView
        val headerView = navigationView.getHeaderView(0)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        setContentView(mainActivityBinding.root)

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")

        val userRef = databaseReference.child(currentUser?.uid!!)
        val nameHeader : TextView = headerView.findViewById(R.id.userNameProfile)
        val emailHeader : TextView = headerView.findViewById(R.id.emailProfile)

        userRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                nameHeader.text = snapshot.child("fullName").value.toString()
                emailHeader.text = snapshot.child("eMail").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "LoadFailed", Toast.LENGTH_SHORT).show()
                Log.d("DBError", error.message)
            }
        })


        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)

        mainActivityBinding.imgMenu.setOnClickListener {
            mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.itemIconTintList = null

        NavigationUI.setupWithNavController(navigationView, navController)


    }

    fun signOut(item: MenuItem){
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}