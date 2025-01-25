package com.gabrielsantana.letsvote.login

import androidx.credentials.GetCredentialRequest

data class LoginUiState(
    val signRequest: GetCredentialRequest? = null,
    val isUserSignedIn: Boolean = false,
    val isError: Boolean =  false,
)