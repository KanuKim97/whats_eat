package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.example.whats_eat.viewModel.activity.FindPwViewModel
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindPwActivity : AppCompatActivity(), View.OnClickListener {
    private val findPwBinding by lazy { ActivityFindPwBinding.inflate(layoutInflater) }
    private val findPwViewModel: FindPwViewModel by viewModels()
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
            R.id.toLoginBtn -> startActivity(Intent(this, LoginActivity::class.java))
            R.id.toSignInBtn -> startActivity(Intent(this, SignInActivity::class.java))
            R.id.send_code -> validateUserEmail(userEmail)
        }
    }

    private fun setUserEmail(): String = findPwBinding.passwordEmailInput.text.toString()

    private fun sendPasswordResetEmail(userEmail: String): Task<Void> =
        findPwViewModel.sendUserPasswordResetEmail(userEmail)

    /* TODO("UI Refactoring") */

    private fun updateUI(): Unit = findPwViewModel.eMailReset.observe(this) {
        if(it) {
            Toast.makeText(this, "고객님의 이메일로 비밀번호 초기화 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Toast.makeText(this, "유효하지 않은 이메일 방식입니다.", Toast.LENGTH_SHORT).show()
            findPwBinding.passwordEmailInput.text?.clear()
        }
    }

    private fun validateUserEmail(userEmail: String) {
        when {
            userEmail.isEmpty() -> {
                findPwBinding.passwordEmailInput.error = "이메일을 입력하세요."
                findPwBinding.passwordEmailInput.text?.clear()
            }
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                findPwBinding.passwordEmailInput.error = "유효하지 않은 이메일 방식입니다."
                findPwBinding.passwordEmailInput.text?.clear()
            }
            else -> {
                sendPasswordResetEmail(userEmail)
                updateUI()
            }
        }
    }

}