package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.viewModel.activity.LoginViewModel
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
        val userEmail = logInBinding.emailInput.text.toString()
        val userPassword = logInBinding.passwordInput.text.toString()

        when (v?.id) {
            R.id.findPwBtn -> startActivity(Intent(this, FindPwActivity::class.java))
            R.id.signUpBtn -> startActivity(Intent(this, SignInActivity::class.java))
            R.id.logInBtn -> loginUserAccount(userEmail, userPassword)
        }
    }

    private fun loginUserAccount(userEmail: String, userPassword: String) {
        loginViewModel.logInUserAccount(userEmail, userPassword)

        if(loginViewModel.userLogInResult.value!!.isSuccessful) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "이메일과 패스워드를 다시 확인해 주세요", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }

}