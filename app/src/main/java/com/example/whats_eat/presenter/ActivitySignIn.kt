package com.example.whats_eat.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySignIn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    private fun validateUserInput() {
//        when {
//            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
//                toastMessage.apply {
//                    setText(R.string.IsNotValidateEmail_Toast)
//                    duration = Toast.LENGTH_SHORT
//                }.show()
//                signInBinding.localEmailInput.text?.clear()
//            }
//            userNickName.length > 15 -> {
//                toastMessage.apply {
//                    setText(R.string.NickNameIsLong_Toast)
//                    duration = Toast.LENGTH_SHORT
//                }.show()
//            }
//            userPassword != confPassword -> {
//                toastMessage.apply {
//                    setText(R.string.PasswordIsNotConfirm_Toast)
//                    duration = Toast.LENGTH_SHORT
//                }.show()
//                signInBinding.localConfPasswordInput.text?.clear()
//            }
//            userNickName.isEmpty() ->
//                signInBinding.localUserNameInput.error = getString(R.string.InputNickName_Error)
//            userFullName.isEmpty() ->
//                signInBinding.localNameInput.error = getString(R.string.InputFullName_Error)
//            userEmail.isEmpty() ->
//                signInBinding.localEmailInput.error = getString(R.string.InputEmail_Error)
//            userPassword.isEmpty() ->
//                signInBinding.localPasswordInput.error = getString(R.string.InputPassword_Error)
//            confPassword.isEmpty() ->
//                signInBinding.localConfPasswordInput.error = getString(R.string.InputConfirmPassword_Error)
//            else -> {
//                createUserAccount(userEmail, userPassword, userFullName, userNickName)
//                updateUI()
//            }
//        }
//    }

}