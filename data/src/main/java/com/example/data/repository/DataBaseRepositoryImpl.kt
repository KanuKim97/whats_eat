package com.example.data.repository

import com.example.data.model.ProfileItem
import com.example.domain.model.placeItem.detailedPlace.DetailedPlace
import com.example.domain.model.CollectionItem
import com.example.domain.repository.DataBaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
): DataBaseRepository {
    private val _userProfile = MutableStateFlow<ProfileItem?>(null)
    private val _collectionItems = MutableStateFlow<ArrayList<DetailedPlace>?>(null)
    private val _collectionItemsCnt = MutableStateFlow<String?>(null)
    val userProfile: Flow<ProfileItem> get() = _userProfile.filterNotNull()
    val collectionItems: Flow<ArrayList<DetailedPlace>> get() = _collectionItems.filterNotNull()
    val collectionItemsCnt: Flow<String> get() = _collectionItemsCnt.filterNotNull()

    private val _currentUser: String by lazy { auth.currentUser?.uid.toString() }
    private val _userReference: DatabaseReference by lazy { database.reference.child(_currentUser) }
    private var eventListener: ValueEventListener? = null
    private var collectionList = ArrayList<DetailedPlace>()

    override suspend fun saveUserProfile(
        userEmail: String,
        userNickName: String,
        userFullName: String
    ) {
        _userReference.child("userEmail").setValue(userEmail).await()
        _userReference.child("userNickName").setValue(userNickName).await()
        _userReference.child("userFullName").setValue(userFullName).await()
    }

    override suspend fun saveUserCollection(collection: CollectionItem) {
        _userReference.child("Collection")
            .push()
            .setValue(collection)
            .await()
    }

    override fun loadUserProfile() {
        eventListener = _userReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userEmail = snapshot.child("userEmail").value.toString()
                val userNickName = snapshot.child("userNickName").value.toString()

                _userProfile.value = ProfileItem(userNickName, userEmail)
            }

            override fun onCancelled(error: DatabaseError) { _userProfile.value = null }
        })
    }

    override fun loadUserAllCollection() {
        eventListener = _userReference.child("collection")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        collectionList.add(it.getValue(DetailedPlace::class.java) as DetailedPlace)
                    }
                    _collectionItems.value = collectionList
                }

                override fun onCancelled(error: DatabaseError) { _collectionItems.value = null }
            })
    }

    override fun loadUserAllCollectionCount(): Task<DataSnapshot> =
        _userReference.child("collection").get()
            .addOnCompleteListener {
                _collectionItemsCnt.value = it.result.childrenCount.toString()
            }.addOnFailureListener { _collectionItemsCnt.value = null }

    fun stopEventListening() {
        eventListener?.apply { _userReference.removeEventListener(this) }
    }

}