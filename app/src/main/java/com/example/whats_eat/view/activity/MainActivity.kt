package com.example.whats_eat.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityMainBinding
import com.example.whats_eat.view.fragment.CollectionFragment
import com.example.whats_eat.view.fragment.HomeFragment
import com.example.whats_eat.view.fragment.MapsFragment
import com.example.whats_eat.view.fragment.ProfileFragment
import com.example.whats_eat.viewModel.activity.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        mainActivityBinding.navigationView.setNavigationItemSelectedListener(this)

        val headerView = mainActivityBinding.navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.userNameProfile)
        val emailHeader: TextView = headerView.findViewById(R.id.emailProfile)

        mainActivityBinding.imgMenu.setOnClickListener {
            mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_container, HomeFragment())
            .commit()

        setContentView(mainActivityBinding.root)
    }

/*

        mainViewModel.getUserData()
        mainViewModel.userData.observe(this) { userData ->
            if(userData.flag) {
                nameHeader.text = userData.fullName
                emailHeader.text = userData.eMail
            }
        }

        mainActivityBinding.imgMenu.setOnClickListener {
            if(!mainActivityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
            } else { mainActivityBinding.drawerLayout.closeDrawer(GravityCompat.START) }
*/


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHome ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_container, HomeFragment())
                    .commit()

            R.id.menuProfile ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_container, ProfileFragment())
                    .commit()

            R.id.menuCollection ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_container, CollectionFragment())
                    .commit()

            R.id.menuMaps ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_container, MapsFragment())
                    .commit()

            R.id.menuSignOut -> {
                // mainViewModel.authSignOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

}