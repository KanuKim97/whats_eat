package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.example.whats_eat.viewModel.activity.FindPwViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var findPwBinding: ActivityFindPwBinding
    private val findPwViewModel: FindPwViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPwBinding = ActivityFindPwBinding.inflate(layoutInflater)
        findPwBinding.sendCode.setOnClickListener(this)
        findPwBinding.toLoginBtn.setOnClickListener(this)
        findPwBinding.toSignInBtn.setOnClickListener(this)

        setContentView(findPwBinding.root)
    }


    override fun onClick(v: View?) {
        val userEmail: String = findPwBinding.passwordEmailInput.text.toString()

        when(v?.id){
            R.id.toLoginBtn -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.toSignInBtn -> startActivity(Intent(this, SignInActivity::class.java))
            R.id.send_code -> validateUserEmail(userEmail)
        }
    }

    private fun validateUserEmail(userEmail: String) {
        when {
            userEmail.isEmpty() -> {
                findPwBinding.passwordEmailInput.error = "Email Required"
                findPwBinding.passwordEmailInput.text?.clear()
            }
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                findPwBinding.passwordEmailInput.error = "Not expression of email patterns"
                findPwBinding.passwordEmailInput.text?.clear()
            }
            else -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }

}