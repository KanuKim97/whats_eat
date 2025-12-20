package com.kanukim97.database

import com.kanukim97.database.dao.EatDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideEatDao(
        eatDataBase: EatDataBase
    ): EatDao = eatDataBase.eatDao()
}