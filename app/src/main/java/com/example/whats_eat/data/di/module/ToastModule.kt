package com.example.whats_eat.data.di.module

import android.content.Context
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ToastModule {
    @Provides
    @Singleton
    fun providesToast(@ApplicationContext context: Context): Toast =
        Toast.makeText(context, "", Toast.LENGTH_SHORT)
}