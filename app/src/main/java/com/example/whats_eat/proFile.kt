package com.example.whats_eat

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whats_eat.databinding.FragmentProFileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class proFile : Fragment() {
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

        userRef.child(auth.currentUser!!.uid).child("Collection")
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.d("count", snapshot.children.count().toString())
                        //TODO : profileBinding Rating Number need to write it
                        //proFileBinding.profileRateNum.text = "${snapshot.children.count()} Stars"
                    }

                    override fun onCancelled(error: DatabaseError) { }
                })

        //TODO : profile updateBtn Function need to be write it
        proFileBinding.updateBtn.setOnClickListener {

        }

        //TODO : User Account Delete Btn Function need to be write it
        proFileBinding.deleteBtn.setOnClickListener {

            val deleteDialog = AlertDialog.Builder(activity)

            deleteDialog.setTitle("Notice!")
                    .setMessage("Do you Want Delete Your Account?")
                    .setPositiveButton("Yes") { dialog, which ->
                        Log.d("Log", dialog.toString())
                        Log.d("Log", which.toString())
                    }
                    .setNegativeButton("No") { dialog, which ->
                        Log.d("Log", dialog.toString())
                        Log.d("Log", which.toString())
                    }.show()

        /*
            //delete UserPart
            currentUser.delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            database.getReference("userInfo").child(currentUser.uid).removeValue()
                            startActivity(Intent(requireContext(), logIn::class.java))
                        }
                    }
        */

        }

        return proFileBinding.root
    }

}