package com.example.whats_eat.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivitySignInBinding
import com.example.whats_eat.viewModel.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySignIn : AppCompatActivity(), View.OnClickListener {
    @Inject lateinit var toastMessage: Toast

    private val signInBinding by lazy { ActivitySignInBinding.inflate(layoutInflater) }
    private val signInViewModel: SignInViewModel by viewModels()

    private val userFullName: String by lazy { setUserFullName() }
    private val userNickName: String by lazy { setUserNickName() }
    private val userEmail: String by lazy { setUserEmail() }
    private val userPassword: String by lazy { setUserPassword() }
    private val confPassword: String by lazy { setConfirmPassword() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding.compBtn.setOnClickListener(this)
        signInBinding.toLoginBtn.setOnClickListener(this)
        setContentView(signInBinding.root)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.compBtn -> validateUserInput()
            R.id.toLoginBtn -> startActivity(Intent(this, ActivityLogIn::class.java))
        }
    }

    private fun setUserFullName(): String = signInBinding.localNameInput.text.toString()

    private fun setUserNickName(): String = signInBinding.localUserNameInput.text.toString()

    private fun setUserEmail(): String = signInBinding.localEmailInput.text.toString()

    private fun setUserPassword(): String = signInBinding.localPasswordInput.text.toString()

    private fun setConfirmPassword(): String = signInBinding.localConfPasswordInput.text.toString()

    private fun createUserAccount(
        Email: String,
        Password: String,
        FullName: String,
        NickName: String
    ): Job = signInViewModel.createAccount(Email, Password, FullName, NickName)

    private fun updateUI(): Unit =
        signInViewModel.isCreateSuccess.observe(this) { isCreateSuccess ->
            if (isCreateSuccess) {
                startActivity(Intent(this, ActivityMain::class.java))
                finish()
            }
        }

    private fun validateUserInput() {
        when {
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                toastMessage.apply {
                    setText(R.string.IsNotValidateEmail_Toast)
                    duration = Toast.LENGTH_SHORT
                }.show()
                signInBinding.localEmailInput.text?.clear()
            }
            userNickName.length > 15 -> {
                toastMessage.apply {
                    setText(R.string.NickNameIsLong_Toast)
                    duration = Toast.LENGTH_SHORT
                }.show()
            }
            userPassword != confPassword -> {
                toastMessage.apply {
                    setText(R.string.PasswordIsNotConfirm_Toast)
                    duration = Toast.LENGTH_SHORT
                }.show()
                signInBinding.localConfPasswordInput.text?.clear()
            }
            userNickName.isEmpty() ->
                signInBinding.localUserNameInput.error = getString(R.string.InputNickName_Error)
            userFullName.isEmpty() ->
                signInBinding.localNameInput.error = getString(R.string.InputFullName_Error)
            userEmail.isEmpty() ->
                signInBinding.localEmailInput.error = getString(R.string.InputEmail_Error)
            userPassword.isEmpty() ->
                signInBinding.localPasswordInput.error = getString(R.string.InputPassword_Error)
            confPassword.isEmpty() ->
                signInBinding.localConfPasswordInput.error = getString(R.string.InputConfirmPassword_Error)
            else -> {
                createUserAccount(userEmail, userPassword, userFullName, userNickName)
                updateUI()
            }
        }
    }

}