package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.view.MainActivity
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var logInBinding: ActivityLogInBinding
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(appRepo = AppRepository())
        loginViewModel = ViewModelProvider(this, vmFactory)[LoginViewModel::class.java]
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(logInBinding.root)

        val googleSignInOption =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOption)
    }

    override fun onResume() {
        super.onResume()

        logInBinding.logInBtn.setOnClickListener(this)
        logInBinding.findPwBtn.setOnClickListener(this)
        logInBinding.signUpBtn.setOnClickListener(this)

        loginViewModel.loginData.observe(this) {
            when (it) {
                "Success" -> { startActivity(Intent(this, MainActivity::class.java)) }
                "Failed" -> {
                    Toast.makeText(this, getString(R.string.logIn_Failed), Toast.LENGTH_SHORT).show()
                    logInBinding.emailInput.text?.clear()
                    logInBinding.passwordInput.text?.clear()
                }
                else -> { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
            }
        }

    }

    override fun onClick(v: View?){
        when (v?.id) {
            R.id.logInBtn -> validateUserData(
                logInBinding.emailInput.text.toString(),
                logInBinding.passwordInput.text.toString()
            )
            R.id.findPwBtn -> startActivity(Intent(this, FindPwActivity::class.java))
            R.id.signUpBtn -> startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }

    private fun validateUserData(userEmail: String, userPassword: String){
        when {
            (userEmail.isNotEmpty() && userPassword.isNotEmpty()) -> {
                loginViewModel.getUserLogin(userEmail, userPassword)
            }
            userEmail.isEmpty() -> {
                Toast.makeText(this, "Plz Enter Your E-mail", Toast.LENGTH_SHORT).show()
            }
            userPassword.isEmpty() -> {
                Toast.makeText(this, "Plz Enter Your Password", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Plz Enter Your E-mail & Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

}