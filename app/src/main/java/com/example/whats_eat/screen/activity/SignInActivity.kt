package com.example.whats_eat.screen.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.whats_eat.R
import com.example.whats_eat.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var signInBinding: ActivitySignInBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")

        setContentView(signInBinding.root)
    }

    override fun onResume() {
        super.onResume()

        signInBinding.compBtn.setOnClickListener(this)
        signInBinding.toLoginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.compBtn -> {

                val userFullName: String = signInBinding.localNameInput.text.toString()
                val userName: String = signInBinding.localUserNameInput.text.toString()
                val userEmail: String = signInBinding.localEmailInput.text.toString()
                val userPassword: String = signInBinding.localPasswordInput.text.toString()
                val userConfPassword: String = signInBinding.localConfPasswordInput.text.toString()

                when {
                    (validateEmailAddress(userEmail)) && (validateUserName(userName)) -> {
                        onSignUpComplete(
                            userFullName,
                            userName,
                            userEmail,
                            userPassword,
                            userConfPassword)
                    }
                }

            }

            R.id.toLoginBtn -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }

    }


    private fun validateEmailAddress(eMail : String): Boolean {

        return if (Patterns.EMAIL_ADDRESS.matcher(eMail).matches()) { true }
        else {
            Toast.makeText(
                this,
                "Invalid Email Address! Plz Type again",
                Toast.LENGTH_SHORT
            ).show()

            signInBinding.localEmailInput.text?.clear()
            false
        }

    }

    private fun validateUserName(userName: String): Boolean {

        return if(userName.length > 15) {
            Toast.makeText(
                this,
                "UserName is so long!",
                Toast.LENGTH_SHORT
            ).show()

            signInBinding.localUserNameInput.text?.clear()
            false
        } else { true }

    }

    private fun onSignUpComplete(
            fullName : String,
            userName : String,
            eMail : String,
            passWord : String,
            confPassword : String
    ){

        when {
            passWord != confPassword -> {
                Toast.makeText(this, "Password is not correct", Toast.LENGTH_SHORT).show()
                signInBinding.localConfPasswordInput.text?.clear()
            }
            fullName.isEmpty() -> {
                signInBinding.localNameInput.error = "Plz enter your Name"
            }
            userName.isEmpty() -> {
                signInBinding.localUserNameInput.error = "Plz enter your User Name"
            }
            eMail.isEmpty() -> {
                signInBinding.localEmailInput.error = "Plz enter your User Name"
            }
            passWord.isEmpty() -> {
                signInBinding.localPasswordInput.error = "Plz enter your User Name"
            }
            confPassword.isEmpty() -> {
                signInBinding.localConfPasswordInput.error = "Plz enter your User Name"
            }
            else -> {
                auth
                    .createUserWithEmailAndPassword(eMail, passWord)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            val currentUser = auth.currentUser
                            val currentUserDb = databaseReference.child((currentUser?.uid!!))

                            currentUserDb.child("fullName").setValue(fullName)
                            currentUserDb.child("userName").setValue(userName)
                            currentUserDb.child("eMail").setValue(eMail)

                            Toast.makeText(
                                this,
                                "Registration is Complete",
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

                            signInBinding.localNameInput.text?.clear()
                            signInBinding.localUserNameInput.text?.clear()
                            signInBinding.localEmailInput.text?.clear()
                            signInBinding.localPasswordInput.text?.clear()
                            signInBinding.localConfPasswordInput.text?.clear()
                        }

                    }
                    .addOnFailureListener {

                        Toast.makeText(
                            this,
                            it.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

            }

        }

    }


}