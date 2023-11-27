package com.example.whats_eat.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.example.whats_eat.presenter.pages.FindPWDPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityFindPWD : AppCompatActivity() {
    private val findPWDBinding by lazy { ActivityFindPwBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(findPWDBinding.root)
    }


//    private fun validateUserEmail(userEmail: String) {
//        when {
//            userEmail.isEmpty() -> {
//                findPwBinding.passwordEmailInput.error = getString(R.string.InputEmail_Error)
//                findPwBinding.passwordEmailInput.text?.clear()
//            }
//            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
//                findPwBinding.passwordEmailInput.error = getString(R.string.EmailNotExist_Toast)
//                findPwBinding.passwordEmailInput.text?.clear()
//            }
//            else -> {
//                sendPasswordResetEmail(userEmail)
//                updateUI()
//            }
//        }
//    }

}