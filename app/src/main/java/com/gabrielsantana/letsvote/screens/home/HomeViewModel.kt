package com.gabrielsantana.letsvote.screens.home

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val credentialManager: CredentialManager
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
        }
    }
}