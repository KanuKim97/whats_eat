package com.example.whats_eat.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.R
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityMain : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private val mainActivityBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityBinding.BottomNavBar.setOnItemSelectedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.Fragment_container, FragmentHome())
            .commit()

        setContentView(mainActivityBinding.root)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        lifecycleScope.launch(mainDispatcher) {
            when (item.itemId) {
                R.id.menuHome -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, FragmentHome())
                        .commit()
                }
                R.id.menuProfile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, FragmentProfile())
                        .commit()
                }
                R.id.menuCollection -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.Fragment_container, FragmentCollection())
                        .commit()
                }
            }
        }
        return true
    }

}