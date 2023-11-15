package com.example.whats_eat.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.viewModel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class ActivityLogIn : AppCompatActivity(), View.OnClickListener {
    @Inject lateinit var toastMessage: Toast

    private val logInBinding by lazy { ActivityLogInBinding.inflate(layoutInflater) }
    private val loginViewModel: LoginViewModel by viewModels()

    private val userEmail: String by lazy { setUserEmail() }
    private val userPassword: String by lazy { setUserPassword() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateLogInUI()

        logInBinding.logInBtn.setOnClickListener(this)
        logInBinding.findPwBtn.setOnClickListener(this)
        logInBinding.signUpBtn.setOnClickListener(this)

        setContentView(logInBinding.root)
    }

    override fun onClick(v: View?){
        when (v?.id) {
            R.id.findPwBtn -> startActivity(Intent(this, ActivityFindPWD::class.java))
            R.id.signUpBtn -> startActivity(Intent(this, ActivitySignIn::class.java))
            R.id.logInBtn -> loginEmailWithPassword(userEmail, userPassword)
        }
    }

    private fun setUserEmail(): String = logInBinding.emailInput.text.toString()

    private fun setUserPassword(): String = logInBinding.passwordInput.text.toString()

    private fun loginEmailWithPassword(userEmail: String, userPassword: String): Job =
        loginViewModel.userLogIn(userEmail, userPassword)

    private fun updateLogInUI(): Unit =
        loginViewModel.logInResult.observe(this) { isLogInSuccess ->
            if (isLogInSuccess.isSuccess) {
                startActivity(Intent(this, ActivityMain::class.java))
                finish()
            } else {
                toastMessage.apply {
                    setText(getString(R.string.LogIn_Failed))
                    duration = Toast.LENGTH_SHORT
                }.show()
            }
        }

    override fun onDestroy() {
        super.onDestroy()
        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }

}