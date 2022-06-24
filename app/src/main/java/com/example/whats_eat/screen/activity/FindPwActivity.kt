package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivityFindPwBinding
import com.google.firebase.auth.FirebaseAuth

class FindPwActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var findPwBinding: ActivityFindPwBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPwBinding = ActivityFindPwBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        setContentView(findPwBinding.root)
    }

    override fun onResume() {
        super.onResume()

        findPwBinding.sendCode.setOnClickListener(this)
        findPwBinding.toLoginBtn.setOnClickListener(this)
        findPwBinding.toSignInBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id){

            R.id.toLoginBtn -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            R.id.toSignInBtn -> {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }

            R.id.send_code -> {
                val findPwEmail: String = findPwBinding.passwordEmailInput.text.toString()

                if(findPwEmail.isEmpty()) {

                    findPwBinding.passwordEmailInput.error = "Required Email"
                    findPwBinding.passwordEmailInput.text?.clear()

                } else {

                    auth.sendPasswordResetEmail(findPwEmail).addOnCompleteListener {
                        if(it.isSuccessful) {
                            Toast.makeText(this, "Check your Email and reset Password!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "Try again! Something wrong happened!", Toast.LENGTH_SHORT).show()
                            findPwBinding.passwordEmailInput.text?.clear()
                        }
                    }

                }

            }

        }
    }


}