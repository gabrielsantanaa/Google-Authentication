package com.gabrielsantana.letsvote.screens.home

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val credentialManager: CredentialManager
) : ViewModel() {

    private val _uiState =  MutableStateFlow(HomeUiState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {
            Firebase.auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
        }
    }

    fun updateRoomCode(roomCode: String) {
        _uiState.update { it.copy(roomCode = roomCode) }
    }
}