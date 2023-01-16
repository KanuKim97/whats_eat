package com.example.whats_eat.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.view.activity.LoginActivity
import com.example.whats_eat.databinding.ActivityMainBinding
import com.example.whats_eat.view.fragment.CollectionFragment
import com.example.whats_eat.view.fragment.HomeFragment
import com.example.whats_eat.view.fragment.MapsFragment
import com.example.whats_eat.view.fragment.ProfileFragment
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.MainViewModel
import com.google.android.material.navigation.NavigationView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var vmFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        mainViewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_container, HomeFragment())
            .commit()
    }

    override fun onResume() {
        super.onResume()
        mainActivityBinding.navigationView.setNavigationItemSelectedListener(this)
        val headerView = mainActivityBinding.navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.userNameProfile)
        val emailHeader: TextView = headerView.findViewById(R.id.emailProfile)

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
        }
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
                mainViewModel.authSignOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return true
    }

}