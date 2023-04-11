package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.data.common.Constant
import com.example.whats_eat.data.di.coroutineDispatcher.MainDispatcher
import com.example.whats_eat.databinding.ActivityLogoBinding
import com.example.whats_eat.viewModel.activity.LogoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LogoActivity : AppCompatActivity() {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    private val logoBinding by lazy { ActivityLogoBinding.inflate(layoutInflater) }
    private val logoViewModel: LogoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUI()
        setContentView(logoBinding.root)
    }

    private fun updateUI(): Unit = logoViewModel.readFireAuth.observe(this) {
        lifecycleScope.launch(mainDispatcher) {
            if(it) {
                delay(Constant.DELAY_TIME_MILLIS)
                startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                finish()
            } else {
                delay(Constant.DELAY_TIME_MILLIS)
                startActivity(Intent(this@LogoActivity, LoginActivity::class.java))
                finish()
            }
        }
    }

}