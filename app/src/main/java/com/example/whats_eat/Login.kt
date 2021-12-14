package com.example.whats_eat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whats_eat.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logInBinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(logInBinding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        logInBinding.findPwBtn.setOnClickListener {
            startActivity(Intent(this, findPw::class.java))
            finish()
        }

        logInBinding.logInBtn.setOnClickListener {
            val userEmail : String = logInBinding.emailInput.text.toString()
            val userPassword :  String = logInBinding.passwordInput.text.toString()

            if(userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                readUserData(userEmail, userPassword)

            } else {
                when {
                    userEmail.isEmpty() -> {
                        Toast.makeText(this, "Plz Enter Email", Toast.LENGTH_SHORT).show()
                    }
                    userPassword.isEmpty() -> {
                        Toast.makeText(this, "Plz Enter Password", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "Plz Enter Email & Password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        logInBinding.signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
            finish()
        }
    }

    private fun readUserData(userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }


}