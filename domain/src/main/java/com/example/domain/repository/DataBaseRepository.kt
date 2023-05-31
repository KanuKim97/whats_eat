package com.example.domain.repository

import com.example.domain.model.CollectionItem
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot

interface DataBaseRepository {
    suspend fun saveUserProfile(userEmail: String, userNickName: String, userFullName: String)

    suspend fun saveUserCollection(collection: CollectionItem)

    fun loadUserProfile()

    fun loadUserAllCollection()

    fun loadUserAllCollectionCount(): Task<DataSnapshot>
}