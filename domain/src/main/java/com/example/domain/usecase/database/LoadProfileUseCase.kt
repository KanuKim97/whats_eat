package com.example.domain.usecase.database

import com.example.domain.model.ProfileItem
import com.example.domain.repository.DataBaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadProfileUseCase @Inject constructor(
    private val DataBaseRepo: DataBaseRepository
) {
    val userProfile: Flow<ProfileItem> = DataBaseRepo.userProfile
    
    fun loadUserProfile(): Unit = DataBaseRepo.loadUserProfile()
}