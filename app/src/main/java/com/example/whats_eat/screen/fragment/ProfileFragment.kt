package com.example.whats_eat.screen.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.whats_eat.R
import com.example.whats_eat.screen.activity.LoginActivity
import com.example.whats_eat.databinding.FragmentProFileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment(), View.OnClickListener {
    private lateinit var proFileBinding: FragmentProFileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        proFileBinding = FragmentProFileBinding.inflate(layoutInflater)

        return proFileBinding.root
    }

    override fun onResume() {
        super.onResume()

        showUserProfile()
        proFileBinding.updateBtn.setOnClickListener(this)
        proFileBinding.deleteBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v?.id) {

            R.id.updateBtn ->
                Toast.makeText(
                    requireContext(),
                    "not updated yet",
                    Toast.LENGTH_SHORT
                ).show()

            R.id.deleteBtn -> {

                val profileDeleteDialog = AlertDialog.Builder(activity)
                val currentUser = auth.currentUser

                profileDeleteDialog
                    .setTitle("Notice!")
                    .setMessage("Do you Want Delete Your Account?")
                    .setPositiveButton("Yes") { _, _ ->

                        currentUser?.delete()?.addOnCompleteListener {

                            if (it.isSuccessful) {
                                database
                                    .getReference("userInfo")
                                    .child(currentUser.uid)
                                    .removeValue()
                                startActivity(Intent(requireContext(), LoginActivity::class.java))
                            }

                        }

                    }
                    .setNegativeButton("No") { _, _ ->}
                    .show()

            }

        }
    }

    private fun showUserProfile() {
        val currentUser = auth.currentUser
        val userRef = databaseReference.child(currentUser?.uid!!)
        val userColRef = userRef.child("Collection")

        userRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                proFileBinding.nameTxt.text =
                    snapshot.child("userName").value.toString()

                proFileBinding.emailTxt.text =
                    snapshot.child("eMail").value.toString()

            }

            override fun onCancelled(error: DatabaseError) {

                Toast.makeText(
                    requireContext(),
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }
        })

        userColRef
            .get()
            .addOnCompleteListener {

                proFileBinding.profileRateNum.text =
                    it.result.childrenCount.toString()

            }
            .addOnFailureListener {

                Toast.makeText(
                    requireContext(),
                    it.toString(),
                    Toast.LENGTH_SHORT
                ).show()

            }

    }


}