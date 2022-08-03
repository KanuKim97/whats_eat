package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
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
                    findPwBinding.passwordEmailInput.error = "Email Required"
                    findPwBinding.passwordEmailInput.text?.clear()
                } else if(!(Patterns.EMAIL_ADDRESS.matcher(findPwEmail).matches())){
                    findPwBinding.passwordEmailInput.error = "Not expression of email patterns"
                    findPwBinding.passwordEmailInput.text?.clear()
                } else {

                    auth.sendPasswordResetEmail(findPwEmail)
                        .addOnCompleteListener {

                            if (it.isSuccessful) {

                                Toast.makeText(
                                    this,
                                    "Plz Check your Email",
                                    Toast.LENGTH_SHORT
                                ).show()

                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {

                                Toast.makeText(
                                    this,
                                    it.exception?.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                                findPwBinding.passwordEmailInput.text?.clear()
                            }

                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                it.message,
                                Toast.LENGTH_SHORT
                            ).show()

                            findPwBinding.passwordEmailInput.text?.clear()
                        }

                }

            }

        }

    }


}