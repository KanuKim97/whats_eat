package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.viewModel.activity.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var logInBinding: ActivityLogInBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)

        logInBinding.logInBtn.setOnClickListener(this)
        logInBinding.findPwBtn.setOnClickListener(this)
        logInBinding.signUpBtn.setOnClickListener(this)

        setContentView(logInBinding.root)
    }

    override fun onClick(v: View?){
        when (v?.id) {
            R.id.findPwBtn -> startActivity(Intent(this, FindPwActivity::class.java))
            R.id.signUpBtn -> startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }


}