package com.example.whats_eat

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.whats_eat.databinding.ActivityMainBinding
import com.example.whats_eat.presenter.FragmentCollection
import com.example.whats_eat.presenter.FragmentHome
import com.example.whats_eat.presenter.FragmentProfile
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val homeFragment: FragmentHome = FragmentHome()
    private val profileFragment: FragmentProfile = FragmentProfile()
    private val collectionFragment: FragmentCollection = FragmentCollection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFrameLayout()
        mainActivityBinding.BottomNavBar.setOnItemSelectedListener(this)
        setContentView(mainActivityBinding.root)
    }

    private fun initFrameLayout(): Int =
        supportFragmentManager.beginTransaction()
            .replace(R.id.Fragment_container, homeFragment)
            .commit()

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.menuHome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, homeFragment)
                        .commit()
                }
                R.id.menuProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, profileFragment)
                        .commit()
                }
                R.id.menuCollection -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, collectionFragment)
                        .commit()
                }
            }
        return true
    }

}