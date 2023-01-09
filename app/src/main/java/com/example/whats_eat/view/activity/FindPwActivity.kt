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
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.example.whats_eat.viewModel.ViewModelFactory
import com.example.whats_eat.viewModel.activity.FindPwViewModel

class FindPwActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var findPwBinding: ActivityFindPwBinding
    private lateinit var vmFactory: ViewModelFactory
    private lateinit var findPwViewModel: FindPwViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPwBinding = ActivityFindPwBinding.inflate(layoutInflater)
        vmFactory = ViewModelFactory(appRepo = AppRepository())
        findPwViewModel = ViewModelProvider(this, vmFactory)[FindPwViewModel::class.java]

        setContentView(findPwBinding.root)
    }

    override fun onResume() {
        super.onResume()

        findPwViewModel.emailResetValue.observe(this) {
            if(it) {
                Toast.makeText(this, "Check your Email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Error: Email doesn't exist", Toast.LENGTH_SHORT).show()
                findPwBinding.passwordEmailInput.text?.clear()
            }
        }

        findPwBinding.sendCode.setOnClickListener(this)
        findPwBinding.toLoginBtn.setOnClickListener(this)
        findPwBinding.toSignInBtn.setOnClickListener(this)
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
            else -> findPwViewModel.sendPasswordResetMail(userEmail)
        }
    }

}