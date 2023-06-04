package com.example.domain.usecase.database

import com.example.domain.repository.DataBaseRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import javax.inject.Inject

class LoadAllCollectionCountUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    val collectionItemCnt = DataBaseRepo.collectionItemsCnt

    fun loadAllUserCollectionCount(): Task<DataSnapshot> = DataBaseRepo.loadUserAllCollectionCount()
}