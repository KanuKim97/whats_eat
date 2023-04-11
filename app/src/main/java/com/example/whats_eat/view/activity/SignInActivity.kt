package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivitySignInBinding
import com.example.whats_eat.viewModel.activity.SignInViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity(), View.OnClickListener {
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
            R.id.toLoginBtn -> startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setUserFullName(): String = signInBinding.localNameInput.text.toString()

    private fun setUserNickName(): String = signInBinding.localUserNameInput.text.toString()

    private fun setUserEmail(): String = signInBinding.localEmailInput.text.toString()

    private fun setUserPassword(): String = signInBinding.localPasswordInput.text.toString()

    private fun setConfirmPassword(): String = signInBinding.localConfPasswordInput.text.toString()

    private fun createUserAccount(
        eMail: String,
        password: String,
        fullName: String,
        nickName: String
    ): Task<AuthResult> = signInViewModel.createUserAccount(eMail, password, fullName, nickName)

    private fun updateUI(): Unit = signInViewModel.isNewUserResult.observe(this) {
        if(!it) {
            Toast.makeText(this, "이미 가입된 사용자 입니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        } else { startActivity(Intent(this, MainActivity::class.java)) }
    }

    private fun validateUserInput() {
        when {
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                Toast.makeText(this, "유효하지 않은 이메일 주소입니다.", Toast.LENGTH_SHORT).show()
                signInBinding.localEmailInput.text?.clear()
            }
            userNickName.length > 15 -> {
                Toast.makeText(this, "사용자 닉네임이 너무 깁니다.", Toast.LENGTH_SHORT).show()
            }
            userPassword != confPassword -> {
                Toast.makeText(this, "패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                signInBinding.localConfPasswordInput.text?.clear()
            }
            userNickName.isEmpty() -> signInBinding.localUserNameInput.error = "사용자 닉네임을 입력해주세요."
            userFullName.isEmpty() -> signInBinding.localNameInput.error = "사용자 이름을 입력해주세요."
            userEmail.isEmpty() -> signInBinding.localEmailInput.error = "사용자 이메일을 입력해주세요."
            userPassword.isEmpty() -> signInBinding.localPasswordInput.error = "사용자 패스워드를 입력해주세요."
            confPassword.isEmpty() -> signInBinding.localConfPasswordInput.error = "패스워드 확인란에 입력해주세요"
            else -> {
                createUserAccount(userEmail, userPassword, userFullName, userNickName)
                updateUI()
            }
        }
    }

}