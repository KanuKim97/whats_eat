package com.example.whats_eat.viewModel.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whats_eat.data.di.repository.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepo: FirebaseRepository): ViewModel() {
    private val _userNickName = MutableLiveData<String>()
    val userNickName: LiveData<String>
        get() = _userNickName

    fun loadUserAccountInfo() {
        val userInfoRef = firebaseRepo.getUserDBCollectionPath()

        userInfoRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    _userNickName.value = snapshot.child("userNickName").value.toString()
                } else {
                    throw (NullPointerException("Data is Empty"))
                }
            }

            override fun onCancelled(error: DatabaseError) { error.toException().printStackTrace() }
        })
    }
}