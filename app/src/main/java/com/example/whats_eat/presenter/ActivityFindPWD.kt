package com.example.whats_eat.presenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.example.whats_eat.viewModel.FindPWDViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class ActivityFindPWD : AppCompatActivity(), View.OnClickListener {
    @Inject lateinit var toastMessage: Toast

    private val findPwBinding by lazy { ActivityFindPwBinding.inflate(layoutInflater) }
    private val findPwViewModel: FindPWDViewModel by viewModels()

    private val userEmail: String by lazy { setUserEmail() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findPwBinding.sendCode.setOnClickListener(this)
        findPwBinding.toLoginBtn.setOnClickListener(this)
        findPwBinding.toSignInBtn.setOnClickListener(this)

        setContentView(findPwBinding.root)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toLoginBtn -> startActivity(Intent(this, ActivityLogIn::class.java))
            R.id.toSignInBtn -> startActivity(Intent(this, ActivitySignIn::class.java))
            R.id.send_code -> validateUserEmail(userEmail)
        }
    }

    private fun setUserEmail(): String = findPwBinding.passwordEmailInput.text.toString()

    private fun sendPasswordResetEmail(userEmail: String): Job =
        findPwViewModel.sendPasswordResetEmail(userEmail)

    private fun updateUI(): Unit =
        findPwViewModel.sendResetEmail.observe(this) { isEmailSendSuccess ->
            if (isEmailSendSuccess.isSuccess) {
                toastMessage.apply {
                    setText(R.string.SendResetEmail_Toast)
                    duration = Toast.LENGTH_SHORT
                }.show()
                startActivity(Intent(this, ActivityLogIn::class.java))
            } else {
                toastMessage.apply {
                    setText(R.string.EmailNotExist_Toast)
                    duration = Toast.LENGTH_SHORT
                }.show()
                findPwBinding.passwordEmailInput.text?.clear()
            }
        }


    private fun validateUserEmail(userEmail: String) {
        when {
            userEmail.isEmpty() -> {
                findPwBinding.passwordEmailInput.error = getString(R.string.InputEmail_Error)
                findPwBinding.passwordEmailInput.text?.clear()
            }
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                findPwBinding.passwordEmailInput.error = getString(R.string.EmailNotExist_Toast)
                findPwBinding.passwordEmailInput.text?.clear()
            }
            else -> {
                sendPasswordResetEmail(userEmail)
                updateUI()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        findPwBinding.passwordEmailInput.text?.clear()
    }

}