package com.example.whats_eat.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.presenter.pages.LogInPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityLogIn : AppCompatActivity() {
    private val logInBinding by lazy { ActivityLogInBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(logInBinding.root)
    }

//    private fun updateLogInUI() {
//        loginViewModel.logInResult.observe(this) { isLogInSuccess ->
//            if (isLogInSuccess.isSuccess) {
//                startActivity(Intent(this, ActivityMain::class.java))
//                finish()
//            } else {
//                toastMessage.apply {
//                    setText(getString(R.string.LogIn_Failed))
//                    duration = Toast.LENGTH_SHORT
//                }.show()
//            }
//        }
//    }

}