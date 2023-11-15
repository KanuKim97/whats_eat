package com.example.domain.repository

import com.example.domain.model.CollectionItem
import com.example.domain.model.ProfileItem
import com.example.domain.model.placeItem.detailedPlace.DetailedPlace
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

interface DataBaseRepository {
    val userProfile: Flow<ProfileItem>
    val collectionItem: Flow<ArrayList<CollectionItem>>
    val collectionItemsCnt: Flow<String>

    suspend fun saveUserProfile(userEmail: String, userNickName: String, userFullName: String)

    suspend fun saveUserCollection(collection: CollectionItem)

    fun loadUserProfile()

    fun loadUserAllCollection()

    fun loadUserAllCollectionCount(): Task<DataSnapshot>

    fun stopEventListening()
}