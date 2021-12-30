package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.google.firebase.auth.FirebaseAuth

// Find Password -> Activity_FindPassword.xml Controller

class FindPw : AppCompatActivity() {

    private lateinit var findPwBinding: ActivityFindPwBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPwBinding = ActivityFindPwBinding.inflate(layoutInflater)
        setContentView(findPwBinding.root)

        auth = FirebaseAuth.getInstance()

        findPwBinding.sendCode.setOnClickListener {
            val findEmail = findPwBinding.passwordEmailInput.text.toString()

            if(findEmail.isBlank()) {
                findPwBinding.passwordEmailInput.error = "Required Email"

                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(findEmail).addOnCompleteListener {

                if(it.isSuccessful) {
                    Toast.makeText(this, "Check your Email and reset Password!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Login::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
                    findPwBinding.passwordEmailInput.text?.clear()
                }
            }
        }

        findPwBinding.toLoginBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        findPwBinding.toSignInBtn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }


    }
}