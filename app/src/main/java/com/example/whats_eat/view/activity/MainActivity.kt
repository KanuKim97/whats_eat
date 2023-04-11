package com.example.whats_eat.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
    private val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateDrawerLayout()

        mainActivityBinding.navigationView.setNavigationItemSelectedListener(this)
        mainActivityBinding.imgMenu.setOnClickListener {
            mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_container, HomeFragment())
            .commit()

        setContentView(mainActivityBinding.root)
    }

    private fun updateDrawerLayout() {
        val headerView: View = mainActivityBinding.navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.userNameProfile)
        val emailHeader: TextView = headerView.findViewById(R.id.emailProfile)

        mainViewModel.userEmail.observe(this) { emailHeader.text = it }
        mainViewModel.userFullName.observe(this) { nameHeader.text = it }
    }

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
                mainViewModel.signOutUserAccount()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

}