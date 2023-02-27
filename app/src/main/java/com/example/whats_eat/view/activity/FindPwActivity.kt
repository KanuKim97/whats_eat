package com.example.whats_eat.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
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
                findPwBinding.passwordEmailInput.error = "이메일을 입력하세요."
                findPwBinding.passwordEmailInput.text?.clear()
            }
            !(Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) -> {
                findPwBinding.passwordEmailInput.error = "유효하지 않은 이메일 방식입니다."
                findPwBinding.passwordEmailInput.text?.clear()
            }
            else -> {
                findPwViewModel.sendUserPasswordResetEmail(userEmail)
                findPwViewModel.eMailReset.observe(this) {
                    if(it) {
                        Toast.makeText(this, "고객님의 이메일로 비밀번호 초기화 메일을 보냈습니다.", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "유효하지 않은 이메일 방식입니다.", Toast.LENGTH_SHORT).show()
                        findPwBinding.passwordEmailInput.text?.clear()
                    }
                }
            }
        }
    }

}