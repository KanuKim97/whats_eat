package com.example.whats_eat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Home : Fragment() {
    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var databaseReference : DatabaseReference
    private lateinit var homeBinding : FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference
            .child("userInfo")
            .child(currentUser.toString())

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                homeBinding.UserTxt.text = snapshot.child("userName").value.toString()
            }

            override fun onCancelled(error: DatabaseError) { }
        })

        return homeBinding.root
    }

}