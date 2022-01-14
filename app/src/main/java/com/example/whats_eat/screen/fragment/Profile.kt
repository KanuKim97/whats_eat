package com.example.whats_eat.screen.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whats_eat.screen.activity.Login
import com.example.whats_eat.databinding.FragmentProFileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Profile : Fragment() {
    private lateinit var proFileBinding: FragmentProFileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        proFileBinding = FragmentProFileBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("userInfo")

        val currentUser = auth.currentUser
        val userRef = databaseReference.child(currentUser?.uid!!)

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                proFileBinding.nameTxt.text = snapshot.child("userName").value.toString()
                proFileBinding.emailTxt.text = snapshot.child("eMail").value.toString()
            }

            override fun onCancelled(error: DatabaseError) { }
        })

        userRef.child("Collection")
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {

                        proFileBinding.profileRateNum.text = it.result.childrenCount.toString()
                    }
                }

        //TODO : profile updateBtn Function need to be write it
        proFileBinding.updateBtn.setOnClickListener { }

        proFileBinding.deleteBtn.setOnClickListener {
            val deleteDialog = AlertDialog.Builder(activity)

            deleteDialog.setTitle("Notice!")
                    .setMessage("Do you Want Delete Your Account?")
                    .setPositiveButton("Yes") { _, _ ->

                        currentUser.delete().addOnCompleteListener {

                            if(it.isSuccessful) {
                                database.getReference("userInfo").child(currentUser.uid).removeValue()
                                startActivity(Intent(requireContext(), Login::class.java))
                            }

                        }
                    }
                    .setNegativeButton("No") { _, _ -> }.show()
        }

        return proFileBinding.root
    }

}