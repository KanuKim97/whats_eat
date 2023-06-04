package com.example.whats_eat.di.module

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
    fun toastBuilder(@ApplicationContext context: Context, message: String): Toast =
        Toast.makeText(context, message, Toast.LENGTH_LONG)
}