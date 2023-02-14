package com.example.whats_eat.viewModel.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainViewModel(private val appRepo: AppRepository): ViewModel() {
    private val _userData = MutableLiveData<MainData>()

    val userData: LiveData<MainData>
        get() = _userData

    fun getUserData() {
        val userDataRef = appRepo.getUserData()

        userDataRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    val fullName = snapshot.child("fullName").value.toString()
                    val eMail = snapshot.child("eMail").value.toString()
                    _userData.value = MainData(true, fullName, eMail, "")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                _userData.value = MainData(false, "", "", error.toString())
            }
        })
    }

    fun authSignOut() = appRepo.userSignOut()
}