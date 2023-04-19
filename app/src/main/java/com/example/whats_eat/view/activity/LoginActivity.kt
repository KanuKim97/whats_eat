package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.whats_eat.R
import com.example.whats_eat.data.di.coroutineDispatcher.MainDispatcher
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.example.whats_eat.viewModel.LoginViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), View.OnClickListener {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
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
            R.id.findPwBtn -> startActivity(Intent(this, FindPwActivity::class.java))
            R.id.signUpBtn -> startActivity(Intent(this, SignInActivity::class.java))
            R.id.logInBtn -> loginEmailWithPassword(userEmail, userPassword)
        }
    }

    private fun setUserEmail(): String = logInBinding.emailInput.text.toString()

    private fun setUserPassword(): String = logInBinding.passwordInput.text.toString()

    private fun loginEmailWithPassword(userEmail: String, userPassword: String): Task<AuthResult> =
        loginViewModel.logInUserAccount(userEmail, userPassword)

    private fun updateLogInUI(): Unit = loginViewModel.userLogInResult.observe(this) {
        lifecycleScope.launch(mainDispatcher) {
            if (it.isSuccessful) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this@LoginActivity,
                    getString(R.string.LogIn_Failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }

}