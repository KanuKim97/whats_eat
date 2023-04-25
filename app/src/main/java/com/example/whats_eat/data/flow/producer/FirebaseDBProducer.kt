package com.example.whats_eat.data.flow.producer

import com.example.whats_eat.view.dataViewClass.ProfileClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDBProducer @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseDatabase
) {
    private val _userProfile = MutableStateFlow<ProfileClass?>(null)
    private val _userCollectionCount = MutableStateFlow<String?>(null)
    val userProfile: Flow<ProfileClass> get() = _userProfile.filterNotNull()
    val userCollectionCount: Flow<String> get() = _userCollectionCount.filterNotNull()

    private val _currentUser: String by lazy { firebaseAuth.currentUser?.uid.toString() }
    private val _userReference: DatabaseReference by lazy { firebaseDB.reference.child(_currentUser) }

    private var eventListener: ValueEventListener? = null

    suspend fun saveUserProfile(
        userEmail: String,
        userNickName: String,
        userFullName: String
    ) {
        _userReference.child("userEmail").setValue(userEmail).await()
        _userReference.child("userNickName").setValue(userNickName).await()
        _userReference.child("userFullName").setValue(userFullName).await()
    }

    fun loadUserProfile() {
        eventListener = _userReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userEmail = snapshot.child("userEmail").value.toString()
                val userName = snapshot.child("userNickName").value.toString()

                _userProfile.value = ProfileClass(userName, userEmail)
            }

            override fun onCancelled(error: DatabaseError) { _userProfile.value = null }
        })
    }

    fun loadUserCollectionCount() = _userReference.child("collection").get()
        .addOnCompleteListener {
            _userCollectionCount.value = it.result.childrenCount.toString()
        }
        .addOnFailureListener {
            _userCollectionCount.value = null
        }


    fun stopEventListening() {
        eventListener?.apply {
            _userReference.removeEventListener(this)
        }
    }
}