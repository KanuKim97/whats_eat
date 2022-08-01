package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.example.whats_eat.R
import com.example.whats_eat.screen.MainActivity
import com.example.whats_eat.databinding.ActivityLogInBinding

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logInBinding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logInBinding = ActivityLogInBinding.inflate(layoutInflater)

        setContentView(logInBinding.root)

    }

    override fun onResume() {
        super.onResume()

        val googleSignOption =
            GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignOption)
        auth = FirebaseAuth.getInstance()

        logInBinding.logInBtn.setOnClickListener(this)
        logInBinding.findPwBtn.setOnClickListener(this)
        logInBinding.signUpBtn.setOnClickListener(this)

    }

    override fun onClick(v: View?){

        when (v?.id) {

            R.id.logInBtn ->
                validateUserData(
                    logInBinding.emailInput.text.toString(),
                    logInBinding.passwordInput.text.toString()
                )

            R.id.findPwBtn -> {
                startActivity(Intent(this, FindPwActivity::class.java))
                finish()
            }

            R.id.signUpBtn -> {
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }

        }

    }

    override fun onDestroy() {
        super.onDestroy()

        logInBinding.emailInput.text?.clear()
        logInBinding.passwordInput.text?.clear()
    }

    private fun readUserData(userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                logInBinding.emailInput.text?.clear()
                logInBinding.passwordInput.text?.clear()
            }
    }

    private fun validateUserData(userEmail: String, userPassword: String){
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

}