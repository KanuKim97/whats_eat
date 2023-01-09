package com.example.whats_eat.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.view.activity.LoginActivity
import com.example.whats_eat.databinding.ActivityMainBinding
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var vmFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(AppRepository())
        mainViewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
    }

    override fun onResume() {
        super.onResume()

        val navigationView = mainActivityBinding.navigationView
        val headerView = navigationView.getHeaderView(0)
        val nameHeader: TextView = headerView.findViewById(R.id.userNameProfile)
        val emailHeader: TextView = headerView.findViewById(R.id.emailProfile)
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)

        mainActivityBinding.imgMenu.setOnClickListener{
            mainActivityBinding.drawerLayout.openDrawer(GravityCompat.START)
        }

        navigationView.itemIconTintList = null
        NavigationUI.setupWithNavController(navigationView, navController)

        mainViewModel.getUserData()
        mainViewModel.userData.observe(this) {
            if(it.flag) {
                nameHeader.text = it.fullName
                emailHeader.text = it.eMail
            } else { Toast.makeText(this, it.DBException, Toast.LENGTH_SHORT).show() }
        }
    }

    fun signOut(item: MenuItem) {
        mainViewModel.authSignOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}