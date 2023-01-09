package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.data.remote.AppRepository
import com.example.whats_eat.databinding.ActivitySignInBinding
import com.example.whats_eat.view.MainActivity
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.SignInViewModel

class SignInActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var signInBinding: ActivitySignInBinding
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vmFactory = ViewModelFactory(appRepo = AppRepository())
        signInViewModel = ViewModelProvider(this, vmFactory)[SignInViewModel::class.java]
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)

        setContentView(signInBinding.root)
    }

    override fun onResume() {
        super.onResume()

        signInViewModel.signInResult.observe(this) {
            if(it) { startActivity(Intent(this, MainActivity::class.java)) }
            else {
                signInBinding.localNameInput.text?.clear()
                signInBinding.localUserNameInput.text?.clear()
                signInBinding.localEmailInput.text?.clear()
                signInBinding.localPasswordInput.text?.clear()
                signInBinding.localConfPasswordInput.text?.clear()
            }
        }

        signInBinding.compBtn.setOnClickListener(this)
        signInBinding.toLoginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.compBtn -> validateUserInput()
            R.id.toLoginBtn -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun validateUserInput() {
        val fullName: String = signInBinding.localNameInput.text.toString()
        val userName: String = signInBinding.localUserNameInput.text.toString()
        val eMail: String = signInBinding.localEmailInput.text.toString()
        val passWord: String = signInBinding.localPasswordInput.text.toString()
        val confPassword: String = signInBinding.localConfPasswordInput.text.toString()

        when {
            passWord != confPassword -> {
                Toast.makeText(this, "Password is not correct", Toast.LENGTH_SHORT).show()
                signInBinding.localConfPasswordInput.text?.clear()
            }
            !(Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) -> {
                Toast.makeText(this, "Invalid Email Address! Plz Type again", Toast.LENGTH_SHORT).show()
                signInBinding.localEmailInput.text?.clear()
            }

            (userName.length > 15) -> {
                Toast.makeText(this, "UserName is so long!", Toast.LENGTH_SHORT).show()
                signInBinding.localUserNameInput.text?.clear()
            }

            fullName.isEmpty() -> signInBinding.localNameInput.error = "Plz enter your Name"
            userName.isEmpty() -> signInBinding.localUserNameInput.error = "Plz enter your User Name"
            eMail.isEmpty() -> signInBinding.localEmailInput.error = "Plz enter your eMail"
            passWord.isEmpty() -> signInBinding.localPasswordInput.error = "Plz enter your password"
            confPassword.isEmpty() -> signInBinding.localConfPasswordInput.error = "Plz enter conform password"

            else -> signInViewModel.createUserAccount(eMail, passWord, fullName, userName)
        }
    }

}