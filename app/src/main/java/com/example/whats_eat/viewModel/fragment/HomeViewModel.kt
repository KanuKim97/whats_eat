package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.remote.AppRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener

class HomeViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _userData = MutableLiveData<String>()

    val userData: LiveData<String>
        get() = _userData

    fun loadHomeInformation() {
        val userDataRef = appRepo.getUserData()

        userDataRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    if(snapshot.exists()) {
                        _userData.value = snapshot.child("NickName").value.toString()
                    } else { throw(NullPointerException("Data is Empty")) }
                } catch (e: DatabaseException) { e.printStackTrace() }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }
}