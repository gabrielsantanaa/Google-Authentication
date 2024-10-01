package com.gabrielsantana.letsvote.screens.login

import androidx.credentials.GetCredentialRequest

data class LoginUiState(
    val signRequest: GetCredentialRequest? = null,
    val isUserSignedIn: Boolean = false,
    val isError: Boolean =  false
)