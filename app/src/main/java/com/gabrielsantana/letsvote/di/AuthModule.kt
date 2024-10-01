package com.gabrielsantana.letsvote.di

import android.content.Context
import androidx.credentials.CredentialManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    fun provideCredentialManager(
        @ApplicationContext appContext: Context
    ): CredentialManager {
        return CredentialManager.create(appContext)
    }
}